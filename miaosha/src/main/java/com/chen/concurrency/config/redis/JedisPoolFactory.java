package com.chen.concurrency.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 程强
 * @date 2020年03月05日 9:54
 * @Description:
 */
@Configuration
public class JedisPoolFactory {
    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        /*JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(), 100, properties.getPassword(), 0);*/
        JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(), 100);
        return pool;
    }

}
