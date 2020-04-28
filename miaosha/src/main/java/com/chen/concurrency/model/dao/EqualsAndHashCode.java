package com.chen.concurrency.model.dao;

import lombok.Data;

/**
 * @author 程强
 * @date 2020年04月19日 9:04
 * @Description: 重写equals和hashCode模板
 * 也可以直接添加注解@EqualsAndHashCode（exclude ={"",""}）可以使用逗号分隔，排除不想比对的数据
 */
@Data
public class EqualsAndHashCode {
    private String name;
    private Integer age;

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.name.hashCode();
        result = result * 31 + this.age;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EqualsAndHashCode)) {
            return false;
        }
        EqualsAndHashCode s = (EqualsAndHashCode) obj;
        return s.name.equals(this.name) && s.age.equals(this.age);
    }
}