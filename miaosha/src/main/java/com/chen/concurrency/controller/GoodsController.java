package com.chen.concurrency.controller;

import com.chen.concurrency.model.MiaoshaUser;
import com.chen.concurrency.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 程强
 * @date 2020年03月05日 17:03
 * @Description:
 */
@Controller
@RequestMapping("/system/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    /**
     * 以下方法中的参数注释直接使用MiaoshaUser对象来接收
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String login(Model model,
                        // @CookieValue(value = Constants.COOKIE_TOKEN_NAME,required = false)String cookieToken,
                        // @RequestParam(value = Constants.COOKIE_TOKEN_NAME,required = false)String paramToken,
                        // HttpServletResponse response
                        MiaoshaUser user
    ){
        // if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
        //     return "login";
        // }
        // String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        // MiaoshaUser user = miaoshaUserService.getByToken(response,token);
        logger.info(user.toString());
        model.addAttribute("user", user);
        return "goods_list";
    }


}
