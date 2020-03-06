package com.chen.concurrency.validator;

import com.chen.concurrency.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 程强
 * @date 2020年03月06日 8:47
 * @Description:
 * 自定义参数校验器需要实现ConstraintValidator<目标类型,校验属性类型>接口
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
         required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required){
            return ValidatorUtil.isMobile(value);
        }else {
                if (StringUtils.isEmpty(value)){
                    return true;
                }else {
                    return ValidatorUtil.isMobile(value);
                }
        }
    }
}
