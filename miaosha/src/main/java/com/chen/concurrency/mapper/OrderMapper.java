package com.chen.concurrency.mapper;

import com.chen.concurrency.model.dao.MiaoshaOrder;
import com.chen.concurrency.model.dao.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @author 言少钱
 * @date 2020年03月08日 15:03
 * @Description:
 */
public interface OrderMapper {

    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId")Long goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_chnnel,status,create_date)" +
            "values (#{user_id},#{goods_id},#{goods_name},#{goods_count},#{goods_price},#{order_chnnel},#{status},#{create_date})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

}
