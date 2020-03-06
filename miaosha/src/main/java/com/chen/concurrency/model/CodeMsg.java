package com.chen.concurrency.model;

/**
 * @author 程强
 * @date 2020年03月05日 20:30
 * @Description:
 * 返回状态码和消息封装类
 */
public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**带有参数的CodeMsg*/
    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code, message);
    }
    /**通用的错误码*/
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg ERROR_SERVER = new CodeMsg(500100, "服务端异常");
    /**参数校验异常:%s 百分号s在字符串格式化的时候表示拼接字符串
     * JAVA字符串格式化-String.format()的使用：https://blog.csdn.net/lonely_fireworks/article/details/7962171
     * */
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
    /**登录模块 5002XX*/
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号码不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号码格式错误");
    public static CodeMsg MOBILE_NOT_EXITS = new CodeMsg(500214, "手机号码不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    /**商品模块 5003XX*/

    /**订单模块 5004XX*/

    /**秒杀模块 5005XX*/


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
