package com.chen.concurrency.config.redis.key.impl;

import com.chen.concurrency.config.redis.key.BasePrefix;
import com.chen.concurrency.vo.Constants;

/**
 * @author 程强
 * @date 2020年03月05日 13:02
 * @Description:
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");

}
