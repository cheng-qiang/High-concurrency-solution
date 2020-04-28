package com.chen.concurrency.model.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月07日 9:46
 * @Description:
 */
@Data
public class MiaoshaGoods {
    private Long id;
    private Long goods_id;
    private Double miaosha_price;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;
}
