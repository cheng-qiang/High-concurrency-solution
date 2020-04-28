package com.chen.concurrency.controller;

import com.chen.concurrency.model.Result;
import com.chen.concurrency.model.dao.MiaoshaUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 程强
 * @date 2020年03月08日 21:04
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user){
        return Result.success(user);
    }
}
