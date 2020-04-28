package com.chen.concurrency.service;

import com.chen.concurrency.mapper.GoodsMapper;
import com.chen.concurrency.model.dao.MiaoshaGoods;
import com.chen.concurrency.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月07日 9:59
 * @Description:
 */
@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    public List<GoodsVo> listGoodsVo(){
        return goodsMapper.listGoodsVo();
    }

    public GoodsVo getGoodsVoGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoGoodsId(goodsId);
    }


    public boolean reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoods_id(goodsVo.getId());
        int i = goodsMapper.reduceStock(goods);
        return i > 0;
    }
}
