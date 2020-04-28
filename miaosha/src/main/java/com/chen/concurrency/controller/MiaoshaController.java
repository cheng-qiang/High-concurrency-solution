package com.chen.concurrency.controller;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.config.redis.key.impl.GoodsKey;
import com.chen.concurrency.model.CodeMsg;
import com.chen.concurrency.model.Result;
import com.chen.concurrency.model.dao.MiaoshaOrder;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.model.dao.OrderInfo;
import com.chen.concurrency.model.message.MiAoShaMessage;
import com.chen.concurrency.rabbitmq.MqSender;
import com.chen.concurrency.service.GoodsService;
import com.chen.concurrency.service.MiaoshaService;
import com.chen.concurrency.service.OrderService;
import com.chen.concurrency.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 程强
 * @date 2020年03月08日 14:40
 * @Description:
 */
@Controller
@RequestMapping("/system/miaosha/")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MqSender mqSender;

    /** **/
    private Map<Long,Boolean> localOverMap = new HashMap<Long,Boolean>();

    /**
     * 系统初始化：InitializingBean实现此接口回调afterPropertiesSet()方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null){
            return;
        }
        for (GoodsVo good: goodsList) {
            redisService.set(GoodsKey.getMiAoShaGoodsStock, ""+good.getId(), good.getStock_count());
            localOverMap.put(good.getId(), false);
        }
    }

    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if (user == null){
            return "login";
        }
        //库存判断
        GoodsVo goodsVo = goodsService.getGoodsVoGoodsId(goodsId);
        int stock_count = goodsVo.getStock_count();
        if (stock_count <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //是否已经秒杀（只能秒杀一次）
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //秒杀：减库存 下订单 写入秒杀订单（事务操作）
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }
   /********************************************************************************************************/
    /**
     * 秒杀接口优化设计流程
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/do_miaosha2")
    @ResponseBody
    public Result<Integer> do_miaosha2(MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //利用内存标记，减少Redis访问
        Boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //预减库存
        Long stock = redisService.decr(GoodsKey.getMiAoShaGoodsStock, "" + goodsId);
        if (stock < 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //是否已经秒杀（只能秒杀一次）
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiAoShaMessage message = new MiAoShaMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        mqSender.sendMiAoShaMessage(message);
        return Result.success(0);
        //排队中，客户端轮询
    }

    /**
     * 客户端轮询查询接口
     * 成功 - orderId
     * 失败 - -1
     * 排队中 - 0
     * @param user
     * @param goodsId
     * @return
     */
    public Result<Long> miaoshaResult(MiaoshaUser user,@RequestParam("goodsId") Long goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);
        return Result.success(result);
    }


}
