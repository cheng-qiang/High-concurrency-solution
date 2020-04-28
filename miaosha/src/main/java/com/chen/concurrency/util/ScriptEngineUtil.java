package com.chen.concurrency.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author 言少钱
 * @date 2020年04月28日 13:43
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
public class ScriptEngineUtil {

    /**
     * 获取秒杀数学公式验证码结果,在获取秒杀接口地址的时候校验
     * @param exp
     * @return
     */
    public static int getMiAoShaVerifyCodeResult(String exp){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        try {
            return ((Integer) engine.eval(exp));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
