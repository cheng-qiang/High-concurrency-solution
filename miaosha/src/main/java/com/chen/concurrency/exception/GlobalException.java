package com.chen.concurrency.exception;

import com.chen.concurrency.model.CodeMsg;

/**
 * @author 程强
 * @date 2020年03月06日 9:34
 * @Description:
 * 全局异常
 */
public class GlobalException extends RuntimeException {

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
