package com.chen.concurrency.model.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 程强
 * @date 2020年03月07日 9:46
 * @Description:
 */
@Data
public class Goods {
    private Long id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private Double goods_price;
    private Integer goods_stock;
}
