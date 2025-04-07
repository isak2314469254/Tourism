package com.trytry.lasttry.impl;

import com.trytry.lasttry.mapper.UserMapper;
import com.trytry.lasttry.pojo.LoginInfo;
import com.trytry.lasttry.pojo.User;
import com.trytry.lasttry.service.UserService;
import com.trytry.lasttry.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //登录
    @Override
    public LoginInfo login(User user) {
        //根据用户名和密码查询用户
        User userLogin = userMapper.getUsername(user.getUsername());
        if(userLogin == null)   return null;//用户不存在
        if(passwordEncoder.matches(user.getPassword(), userLogin.getPassword())) {
            //密码匹配
            return new LoginInfo(userLogin.getId(), JwtUtil.generateToken(userLogin.getUsername()));
        }
        return null;//密码错误
    }

    //注册
    @Override
    public int register(User user) {
        User existUser = userMapper.getUsername(user.getUsername());
        if (existUser != null) {
            return 0;//用户名已存在
        }

        //加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //插入数据库
        if (userMapper.insertUser(user) == 1) {
            //如果成功插入
            return 1;//注册成功
        }
        return -1;//注册失败
    }

}
