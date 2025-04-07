package com.trytry.lasttry.service;

import com.trytry.lasttry.pojo.Tag;

import java.util.List;

public interface TagService {

    //获取所有的兴趣标签
    public List<Tag> getAllTags();

    //更新用户的兴趣标签
    public void updateInterests(Integer user_id, List<Integer> tagIds);

    //获取用户的兴趣标签
    public List<Tag> getTagsByUserId(Integer user_id);
}
