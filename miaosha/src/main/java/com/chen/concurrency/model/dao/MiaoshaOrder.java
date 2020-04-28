package com.chen.concurrency.model.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 程强
 * @date 2020年03月07日 9:47
 * @Description:
 */
@Getter
@Setter
public class MiaoshaOrder {
    private Long id;
    private Long user_id;
    private Long order_id;
    private Long goods_id;
}
