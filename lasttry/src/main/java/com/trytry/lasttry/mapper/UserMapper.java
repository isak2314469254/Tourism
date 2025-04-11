package com.trytry.lasttry.mapper;

import com.trytry.lasttry.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    //查询数据库
    @Select("SELECT * FROM user WHERE username = #{username} and password = #{password}")
    User getUsernameAndPassword(String username, String password);

    //查询数据库,是否已存在该用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUsername(String username);

    //插入数据库
    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    int insertUser(User user);

    //根据用户id查询用户名
    @Select("SELECT username FROM user WHERE id = #{userId}")
    String getUsernameById(Integer userId);
}

