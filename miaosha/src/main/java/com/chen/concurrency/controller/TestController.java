package com.chen.concurrency.controller;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.model.dao.User;
import com.chen.concurrency.config.redis.key.impl.UserKey;
import com.chen.concurrency.rabbitmq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 程强
 * @date 2020年03月05日 11:14
 * @Description:
 */
@Controller
@RequestMapping("/system/test")
public class TestController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private MqSender mqSender;

    @GetMapping("/send")
    @ResponseBody
    public String toSend(){
        mqSender.send("hello,yiwannuofulais@163.com");
        mqSender.sendTopic("hello;topic success");
        return "hello chen";
    }

    @RequestMapping("/")
    @ResponseBody
    public User redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("张三");
        redisService.set(UserKey.getByID,"1", user);
        User user1 = redisService.get(UserKey.getByID,"1",User.class);
        return user1;
    }

}
