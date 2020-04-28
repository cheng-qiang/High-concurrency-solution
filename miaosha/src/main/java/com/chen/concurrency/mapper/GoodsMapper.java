package com.chen.concurrency.mapper;

import com.chen.concurrency.model.dao.MiaoshaGoods;
import com.chen.concurrency.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 言少钱
 * @date 2020年03月07日 10:00
 * @Description:
 */
public interface GoodsMapper {

    /**
     * 获取商品列表
     * @return
     */
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    /**
     * 获取商品
     * @param goodsId
     * @return
     */
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoGoodsId(@Param("goodsId") long goodsId);

    /**
     * 减少库存-
     * @param goods
     * @return
     */
    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goods_id} and stock_count > 0")
    int reduceStock(MiaoshaGoods goods);
}
