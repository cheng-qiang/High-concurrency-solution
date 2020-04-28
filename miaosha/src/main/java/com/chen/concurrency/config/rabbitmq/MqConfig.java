package com.chen.concurrency.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 言少钱
 * @date 2020年04月27日 17:16
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：RabbitMQ-4种模式：https://app.yinxiang.com/fx/bd339482-0c26-4134-8d8c-06fad54bce3a
 * @Description:
 */
@Configuration
public class MqConfig {

    public static final String MI_AO_SHA_QUEUE = "mi.ao.sha.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";

    /**
     * 秒杀队列
     * @return
     */
    @Bean
    public Queue mi_ao_sha_queue(){
        return new Queue(MI_AO_SHA_QUEUE,true);
    }

    /**
     * Direct模式 交换机Exchange
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }

    /**
     * Topic模式 交换机Exchange
     * @return
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new  TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }




































}
