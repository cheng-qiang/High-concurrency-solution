package com.chen.concurrency.rabbitmq;

import com.chen.concurrency.config.rabbitmq.MqConfig;
import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.model.CodeMsg;
import com.chen.concurrency.model.dao.MiaoshaOrder;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.model.message.MiAoShaMessage;
import com.chen.concurrency.service.GoodsService;
import com.chen.concurrency.service.MiaoshaService;
import com.chen.concurrency.service.OrderService;
import com.chen.concurrency.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 言少钱
 * @date 2020年04月27日 17:14
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:消费者
 */
@Service
public class MqReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    private static final Logger logger = LoggerFactory.getLogger(MqReceiver.class);


    @RabbitListener(queues = MqConfig.MI_AO_SHA_QUEUE)
    public void receive(String message){
        logger.info("收到："+message);
        MiAoShaMessage miAoShaMessage = RedisService.stringToBean(message, MiAoShaMessage.class);
        MiaoshaUser user = miAoShaMessage.getUser();
        long goodsId = miAoShaMessage.getGoodsId();
        //库存判断
        GoodsVo goodsVo = goodsService.getGoodsVoGoodsId(goodsId);
        int stock_count = goodsVo.getStock_count();
        if (stock_count <= 0){
            return;
        }
        //是否已经秒杀（只能秒杀一次）
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            return;
        }
        //秒杀：减库存 下订单 写入秒杀订单（事务操作）
        miaoshaService.miaosha(user,goodsVo);

    }

    // @RabbitListener(queues = MqConfig.QUEUE)
    // public void receive(String message){
    //     logger.info("收到："+message);
    // }

    // @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    // public void receiveTopic1(String message){
    //     logger.info("Topic queue1 message："+message);
    // }
    //
    // @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    // public void receiveTopic2(String message){
    //     logger.info("Topic queue2 message："+message);
    // }
}
