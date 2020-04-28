package com.chen.concurrency.model.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月07日 9:46
 * @Description:
 */
@Setter
@Getter
public class OrderInfo {
    private Long id;
    private Long user_id;
    private Long goods_id;
    private Long delivery_addr_id;
    private String goods_name;
    private Integer goods_count;
    private Double goods_price;
    private Integer order_chnnel;
    private Integer status;
    private Date create_date;
    private Date pay_date;
}
