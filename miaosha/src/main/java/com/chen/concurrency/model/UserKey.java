package com.chen.concurrency.model;

import com.chen.concurrency.config.redis.key.BasePrefix;

/**
 * @author 程强
 * @date 2020年03月05日 13:02
 * @Description:
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getByID = new UserKey("id");

}
