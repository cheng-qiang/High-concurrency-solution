package com.chen.concurrency.controller;

import com.chen.concurrency.model.CodeMsg;
import com.chen.concurrency.model.Result;
import com.chen.concurrency.service.MiaoshaUserService;
import com.chen.concurrency.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author 程强
 * @date 2020年03月05日 17:03
 * @Description:
 */
@Controller
@RequestMapping("/system/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String login(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        return Result.success(miaoshaUserService.Login(response,loginVo));
    }
}
