package com.chen.concurrency.vo;

import com.chen.concurrency.model.dao.Goods;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月07日 10:03
 * @Description:
 */
@Data
public class GoodsVo extends Goods {
    private Double miaosha_price;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;
}
