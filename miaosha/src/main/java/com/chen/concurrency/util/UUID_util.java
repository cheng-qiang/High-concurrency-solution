package com.chen.concurrency.util;

import java.util.UUID;

/**
 * @author 程强
 * @date 2020年03月06日 10:11
 * @Description:
 */
public class UUID_util {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
