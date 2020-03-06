package com.chen.concurrency.config.redis.key;

/**
 * @author 程强
 * @date 2020年03月05日 12:27
 * @Description:
 * key前缀接口
 */
public interface KeyPrefix {
    /**
     * 获取key前缀的过期时间方法
     * @return
     */
    public int expireSeconds();

    /**
     * 获取key前缀的方法
     * @return
     */
    public String getPrefix();
}
