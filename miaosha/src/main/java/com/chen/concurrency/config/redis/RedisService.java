package com.chen.concurrency.config.redis;

import com.alibaba.fastjson.JSON;
import com.chen.concurrency.config.redis.key.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 程强
 * @date 2020年03月05日 9:58
 * @Description:
 */
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 通过键值获取单个对象
     * */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String real_key = prefix.getPrefix()+key;
            String str = jedis.get(real_key);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过键值对的形式加入到缓存中
     * */
    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length()<=0){
                return false;
            }
            String real_key = prefix.getPrefix()+key;
            int seconds = prefix.expireSeconds();
            if (seconds<=0){
                jedis.set(real_key, str);
            }else {
                jedis.setex(real_key, seconds, str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Boolean exits(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String real_key = prefix.getPrefix()+key;
            return jedis.exists(real_key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 可以对指定 key 的 value 执行加 1 操作，
     * 如果指定的 key 不存在，那么在加 1 操作之前，会先将 key 的 value 设置为0 ，
     * 如果 key 的value 不是数字，则会报错
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String real_key = prefix.getPrefix()+key;
            return jedis.incr(real_key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 可以对指定key的value执行 减1 操作，
     * 如果 key 不存在，则 key 对应的初始值会被置为 0 ，
     * 如果 key 的 value 不为数字，则会报错
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String real_key = prefix.getPrefix()+key;
            return jedis.decr(real_key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 对象 --> 字符串
     * */
    private <T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 字符串 --> 对象
     * */
    private <T> T stringToBean(String str,Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }

    }

    /**
     * 释放资源还给连接池
     * */
    private void returnToPool(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }
}
