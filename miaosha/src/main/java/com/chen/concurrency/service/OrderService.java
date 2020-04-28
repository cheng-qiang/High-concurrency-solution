package com.chen.concurrency.service;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.config.redis.key.impl.OrderKey;
import com.chen.concurrency.mapper.MiaoshaMapper;
import com.chen.concurrency.mapper.OrderMapper;
import com.chen.concurrency.model.dao.MiaoshaOrder;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.model.dao.OrderInfo;
import com.chen.concurrency.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月08日 14:50
 * @Description:
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MiaoshaMapper miaoshaMapper;

    @Autowired
    private RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreate_date(new Date());
        orderInfo.setDelivery_addr_id(0L);
        orderInfo.setGoods_count(1);
        orderInfo.setGoods_id(goodsVo.getId());
        orderInfo.setGoods_name(goodsVo.getGoods_name());
        orderInfo.setGoods_price(goodsVo.getMiaosha_price());
        //下订单渠道： 1pc,2android,3ios
        orderInfo.setOrder_chnnel(1);
        //订单状态：0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
        orderInfo.setStatus(0);
        orderInfo.setUser_id(user.getId());
        orderMapper.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUser_id(user.getId());
        miaoshaOrder.setGoods_id(goodsVo.getId());
        miaoshaOrder.setOrder_id(orderInfo.getId());
        miaoshaMapper.miaoshaInsert(miaoshaOrder);

        redisService.set(OrderKey.getMiAoShaOrderByUidGid,""+user.getId()+"_"+goodsVo.getId(),miaoshaOrder);

        return orderInfo;
    }
}
