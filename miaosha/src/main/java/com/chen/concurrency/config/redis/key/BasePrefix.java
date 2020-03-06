package com.chen.concurrency.config.redis.key;

import com.chen.concurrency.config.redis.key.KeyPrefix;

/**
 * @author 程强
 * @date 2020年03月05日 12:31
 * @Description:
 * 通用key前缀抽象类
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    /**
     * 默认0代表永不过期
     * @param prefix
     */
    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String class_name = getClass().getSimpleName();
        return class_name+":"+prefix;
    }
}
