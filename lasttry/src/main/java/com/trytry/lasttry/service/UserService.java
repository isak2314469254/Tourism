package com.trytry.lasttry.service;

import com.trytry.lasttry.pojo.User;
import com.trytry.lasttry.pojo.LoginInfo;

public interface UserService {


    LoginInfo login(User user);
    //注册
    int register(User user);


}
