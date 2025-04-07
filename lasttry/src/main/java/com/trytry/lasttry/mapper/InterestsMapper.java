package com.trytry.lasttry.mapper;

import com.trytry.lasttry.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterestsMapper {
    //得到所有的兴趣标签
    @Select("SELECT * FROM interest_tags")
    List<Tag> findAllTags();

    //删除用户兴趣关联
    @Delete("DELETE FROM user_interests WHERE user_seek = #{user_id}")
    void deleteByUserId(@Param("user_id") Integer user_id);

    //插入用户兴趣关联
    @Insert({
            "<script>",
            "INSERT INTO user_interests (user_seek, tag_seek) VALUES",
            "<foreach collection='tagIds' item='tagId' separator=','>",
            "(#{user_seek}, #{tagId})",
            "</foreach>",
            "</script>"
    })
    void insertUserTags(@Param("user_seek") Integer user_id, @Param("tagIds") List<Integer> tagIds);

    //根据用户id查询兴趣标签
    @Select("SELECT * FROM interest_tags WHERE interest_tags.tag_id IN (SELECT tag_seek FROM user_interests WHERE user_seek = #{user_id})")
    List<Tag> findTagsByUserId(@Param("user_id") Integer user_id);
}
