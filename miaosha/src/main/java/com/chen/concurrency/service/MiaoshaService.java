package com.chen.concurrency.service;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.config.redis.key.impl.MiaoshaKey;
import com.chen.concurrency.model.dao.MiaoshaOrder;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.model.dao.OrderInfo;
import com.chen.concurrency.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 程强
 * @date 2020年03月08日 14:56
 * @Description:
 */
@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //减库存 下订单 写入秒杀订单（事务操作）
        boolean success = goodsService.reduceStock(goodsVo);
        if (success){
            //order_info miaosha_order
            return orderService.createOrder(user,goodsVo);
        }else {
            //下单失败,做个标记
            setGoodsOver(goodsVo.getId());
            return null;
        }

    }

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(Long userId, Long goodsId) {
        MiaoshaOrder m = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        //秒杀成功返回订单ID
        if (m != null){
            return m.getOrder_id();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                //秒杀失败返回-1
                return -1;
            }else {
                //排队中返回0继续轮询
                return 0;
            }
        }

    }

    /**
     *
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    /**
     *
     * @param goodsId
     * @return
     */
    private boolean getGoodsOver(Long goodsId) {
      return  redisService.exits(MiaoshaKey.isGoodsOver,""+goodsId);
    }
}
