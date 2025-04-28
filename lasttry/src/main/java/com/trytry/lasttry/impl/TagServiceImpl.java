package com.trytry.lasttry.impl;


import com.trytry.lasttry.mapper.InterestsMapper;
import com.trytry.lasttry.pojo.Tag;
import com.trytry.lasttry.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired InterestsMapper interestsMapper;

    //得到所有的兴趣标签
    @Override
    public List<Tag> getAllTags() {
        return interestsMapper.findAllTags();
    }

    //更新兴趣标签
    @Override
    public void updateInterests(Integer user_id, List<Integer> tagIds) {
        //先清空所有兴趣标签，再重新载入选择的兴趣标签
        interestsMapper.deleteByUserId(user_id);
        interestsMapper.insertUserTags(user_id, tagIds);
    }

    @Override
    public List<Tag> getTagsByUserId(Integer user_id) {
        return interestsMapper.findTagsByUserId(user_id);
    }

    //通过多个标签ID获得标签内容
    @Override
    public List<String> getTagsByIds(List<Integer> tagIds){
        return interestsMapper.findTagsByTagIds(tagIds);
    }

    //orderBy的映射
    public String mapOrderBy(String orderBy){
        return switch (orderBy) {
            case "heat" -> "views";
            case "rating" -> "avg_rating";
            default -> throw new IllegalArgumentException("Invalid orderBy value");
        };
    }
}
