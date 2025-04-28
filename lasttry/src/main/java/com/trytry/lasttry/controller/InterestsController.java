package com.trytry.lasttry.controller;


import com.trytry.lasttry.pojo.InterestsInfo;
import com.trytry.lasttry.pojo.Result;
import com.trytry.lasttry.pojo.Tag;
import com.trytry.lasttry.pojo.TagRequest;
import com.trytry.lasttry.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class InterestsController {

    @Autowired
    private TagService tagService;

    //获取所有兴趣标签
    @GetMapping("/api/interest-tags")
    public Result getInterests(){
        log.info("获取兴趣标签");
        List<Tag> tags = tagService.getAllTags();
        return Result.success(tags, "获取兴趣标签成功");
    }

    //更新用户的兴趣标签
    @PutMapping("/api/users/{user_id}/interests")
    public Result updateInterests(@PathVariable("user_id") Integer user_id,
                                  @RequestBody TagRequest tagRequest){
        log.info("更新兴趣标签");
        List<Integer> tagIds = tagRequest.getTagIds();
        log.info("Received tagIds: {}", tagIds);
        tagService.updateInterests(user_id, tagIds);
        InterestsInfo interestsInfo = new InterestsInfo(user_id, tagService.getTagsByIds(tagIds));
        return Result.success(interestsInfo, "更新兴趣标签成功");
    }

    //获得用户的兴趣标签
    @GetMapping("/api/users/{user_id}/interests")
    public Result getInterests(@PathVariable("user_id") Integer user_id){
        log.info("获取用户兴趣标签");
        List<Tag> tags = tagService.getTagsByUserId(user_id);
        return Result.success(tags, "获取用户兴趣标签成功");
    }
}
