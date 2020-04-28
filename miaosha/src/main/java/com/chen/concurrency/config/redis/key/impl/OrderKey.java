package com.chen.concurrency.config.redis.key.impl;

import com.chen.concurrency.config.redis.key.BasePrefix;
import com.chen.concurrency.vo.Constants;

/**
 * @author 程强
 * @date 2020年03月05日 13:02
 * @Description:
 */
public class OrderKey extends BasePrefix {

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static OrderKey getMiAoShaOrderByUidGid = new OrderKey(0,"ol");

}
