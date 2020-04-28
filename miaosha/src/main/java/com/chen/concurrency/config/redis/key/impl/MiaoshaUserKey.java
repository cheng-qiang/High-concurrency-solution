package com.chen.concurrency.config.redis.key.impl;

import com.chen.concurrency.config.redis.key.BasePrefix;
import com.chen.concurrency.vo.Constants;

/**
 * @author 程强
 * @date 2020年03月06日 10:19
 * @Description:
 */
public class MiaoshaUserKey extends BasePrefix {

    private MiaoshaUserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static MiaoshaUserKey token = new MiaoshaUserKey(Constants.TOKEN_EXPIRE,"token");
    public static MiaoshaUserKey getUserByID = new MiaoshaUserKey(0,"token");
}
