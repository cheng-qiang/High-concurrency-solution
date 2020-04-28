package com.chen.concurrency.controller;

import com.chen.concurrency.config.redis.RedisService;
import com.chen.concurrency.config.redis.key.impl.GoodsKey;
import com.chen.concurrency.model.dao.MiaoshaUser;
import com.chen.concurrency.service.GoodsService;
import com.chen.concurrency.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 程强
 * @date 2020年03月05日 17:03
 * @Description:
 */
@Controller
@RequestMapping("/system/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 页面缓存:一般有效期比较短!
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        if (null == user){
            return "login";
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        logger.info(goodsList.toString());
        model.addAttribute("goodsList",goodsList);
        // return "goods_list";
        WebContext wc = new WebContext( request, response, request.getServletContext(), request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",wc);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{id}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user,
                         @PathVariable("id")Long goodsId){
        //取缓存--不同商品不同详情页面（带参数设置缓存也叫URL缓存）
        String html = redisService.get(GoodsKey.getGoodsDetails, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoGoodsId(goodsId);
        model.addAttribute("goods",goods);
        logger.info(goods.toString());
        //秒杀商品
        long startAt = goods.getStart_date().getTime();
        long entAt = goods.getEnd_date().getTime();
        long nowAt = System.currentTimeMillis();
        int miaoshaoStatus = 0;
        int remainSeconds = 0;
        if (nowAt < startAt){//秒杀没开始，倒计时
            miaoshaoStatus = 0;
            remainSeconds = (int)((startAt-nowAt)/1000);
        }else if(nowAt > entAt){//秒杀结束
            miaoshaoStatus = 2;
            remainSeconds = -1;
        }else {//秒杀正在进行中
            miaoshaoStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaoStatus",miaoshaoStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        //return "goods_detail";
        WebContext wc = new WebContext( request, response, request.getServletContext(), request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",wc);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetails, ""+goodsId, html);
        }
        return html;
    }

}
