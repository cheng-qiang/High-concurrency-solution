package com.chen.concurrency.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 程强
 * @date 2020年03月05日 16:29
 * @Description:
 * 两次MD5加密
 */
public class Md5_util {

    private static final String SALT = "1a2b3c4d";

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    /**
     * 第一次MD5
     * */
    public static String inputPassToFormPass(String inputPass){
        String str = "" + SALT.charAt(0)+SALT.charAt(2)+inputPass+SALT.charAt(5)+SALT.charAt(4);
        return md5(str);
    }

    /**
     * 第二次MD5
     * */
    public static String formPassToDBPass(String formPass,String salt){
        String str = "" + salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 获取加密后的密码
     * */
    public static String inputPassToDBPass(String input,String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }

}
