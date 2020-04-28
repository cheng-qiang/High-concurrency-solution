package com.chen.concurrency.config.redis.key.impl;

import com.chen.concurrency.config.redis.key.BasePrefix;
import com.chen.concurrency.vo.Constants;

/**
 * @author 程强
 * @date 2020年03月05日 13:02
 * @Description:
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(Constants.PAGE_EXPIRE,"gl");

    public static GoodsKey getGoodsDetails = new GoodsKey(Constants.PAGE_EXPIRE,"gd");

    public static GoodsKey getMiAoShaGoodsStock = new GoodsKey(0,"gs");

}
