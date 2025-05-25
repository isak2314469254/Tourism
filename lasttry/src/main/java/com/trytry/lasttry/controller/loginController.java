package com.trytry.lasttry.controller;

import com.trytry.lasttry.pojo.LoginInfo;
import com.trytry.lasttry.pojo.Result;
import com.trytry.lasttry.pojo.User;
import com.trytry.lasttry.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class loginController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public Result login(@RequestBody User user){
        log.info("员工来登录啦 , {}", user);
        LoginInfo loginInfo = userService.login(user);
        if(loginInfo != null){
            return Result.success(loginInfo, "登录成功~");
        }
        return Result.error("用户名或密码错误~");
    }

    @PostMapping("/api/register")
    public Result register(@RequestBody User user){
        log.info("员工来注册啦 , {}", user);
        int success = userService.register(user);
        LoginInfo loginInfo = userService.login(user);
        if(success == 1){
            return Result.success(loginInfo, 200, "注册成功~");
        }
        else if(success == 0){
            return Result.error("用户名已存在~");
        }
        return Result.error("注册失败~");
    }

}
