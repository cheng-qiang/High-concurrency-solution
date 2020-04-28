package com.chen.concurrency.service;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.config.redis.key.impl.MiaoshaUserKey;
import com.chen.concurrency.exception.GlobalException;
import com.chen.concurrency.mapper.MiaoshaUserMapper;
import com.chen.concurrency.model.CodeMsg;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.util.Md5_util;
import com.chen.concurrency.util.UUID_util;
import com.chen.concurrency.vo.Constants;
import com.chen.concurrency.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 程强
 * @date 2020年03月05日 21:20
 * @Description:
 */
@Service
public class MiaoshaUserService {

    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 对象缓存
     * @param id
     * @return
     */
    public MiaoshaUser getById(Long id){
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getUserByID, "" + id, MiaoshaUser.class);
        if (user != null){
            return user;
        }
        user = miaoshaUserMapper.getById(id);
        if (user != null){
            redisService.set(MiaoshaUserKey.getUserByID, ""+id, user);
        }
        return user;
    }

    /**
     * 对象缓存时注意处理缓存
     * @param token
     * @param id
     * @param formPass
     * @return
     */
    public boolean updatePassword(String token,long id,String formPass){
        MiaoshaUser user = getById(id);
        if (user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXITS);
        }
        //更新数据库
        MiaoshaUser updateUser = new MiaoshaUser();
        updateUser.setId(id);
        updateUser.setPassword(Md5_util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserMapper.updateUserPassword(updateUser);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getUserByID, ""+id);
        user.setPassword(updateUser.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    public Boolean Login(HttpServletResponse response,LoginVo loginVo) {
        if (loginVo == null){
             throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXITS);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = Md5_util.formPassToDBPass(formPass, saltDB);
        if (!dbPass.equals(calcPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUID_util.uuid();
        addCookie(response,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response,String token,MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(Constants.COOKIE_TOKEN_NAME, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user!=null){
            addCookie(response,token,user);
        }
        return user;
    }
}
