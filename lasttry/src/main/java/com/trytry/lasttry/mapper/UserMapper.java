package com.trytry.lasttry.mapper;

import com.trytry.lasttry.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username} and password = #{password}")
    User getUsernameAndPassword(String username, String password);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUsername(String username);

    //插入数据库
    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    int insertUser(User user);
}

