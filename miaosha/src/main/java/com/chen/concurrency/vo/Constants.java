package com.chen.concurrency.vo;

/**
 * @author 程强
 * @date 2020年03月06日 11:03
 * @Description:
 * 可扩展的常量接口
 */
public interface Constants {
    /**设置cookie的令牌名称*/
    String COOKIE_TOKEN_NAME = "token";
    /**设置cookie的有效时间*/
    int TOKEN_EXPIRE = 3600*24*2;
    /**设置页面缓存有效时间*/
    int PAGE_EXPIRE = 60;
}
