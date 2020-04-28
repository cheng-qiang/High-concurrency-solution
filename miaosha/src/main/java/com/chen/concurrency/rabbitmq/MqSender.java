package com.chen.concurrency.rabbitmq;

import com.chen.concurrency.config.rabbitmq.MqConfig;
import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.model.message.MiAoShaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 言少钱
 * @date 2020年04月27日 17:15
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:发送者
 */
@Service
public class MqSender {
    private static final Logger logger = LoggerFactory.getLogger(MqSender.class);
    @Autowired
    AmqpTemplate amqpTemplate;

    // public void send(Object message){
    //     String msg = RedisService.beanToString(message);
    //     logger.info("发送："+msg);
    //     amqpTemplate.convertAndSend(MqConfig.QUEUE, msg);
    // }
    //
    // public void sendTopic(Object message){
    //     String msg = RedisService.beanToString(message);
    //     logger.info("send topic msg: "+msg);
    //     amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
    //     amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    // }

    public void sendMiAoShaMessage(MiAoShaMessage message) {
        String msg = RedisService.beanToString(message);
        logger.info("send miAoShaMessage : "+ msg);
        amqpTemplate.convertAndSend(MqConfig.MI_AO_SHA_QUEUE, msg );
    }
}
