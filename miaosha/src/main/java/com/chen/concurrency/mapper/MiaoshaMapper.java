package com.chen.concurrency.mapper;

import com.chen.concurrency.model.dao.MiaoshaOrder;
import org.apache.ibatis.annotations.Insert;

/**
 * @author 言少钱
 * @date 2020年03月08日 15:36
 * @Description:
 */
public interface MiaoshaMapper {

    @Insert("insert into miaosha_order(user_id,order_id,goods_id)values (#{user_id},#{order_id},#{goods_id})")
    void miaoshaInsert(MiaoshaOrder miaoshaOrder);
}
