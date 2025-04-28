package com.trytry.lasttry.mapper;

import com.trytry.lasttry.pojo.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryMapper {

    //创建日记
    @Insert("INSERT INTO diary(user_id, spot_id, title, content) " +
            "VALUES(#{userId}, #{spotId}, #{title}, #{content, typeHandler= com.trytry.lasttry.utils.BlobTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "diaryId")
    int insertDiary(Diary diary);

    //根据日记id获取日记
    @Select("SELECT * FROM diary WHERE id = #{id}")
    @Results({
            @Result(column = "content", property = "content", typeHandler = com.trytry.lasttry.utils.BlobTypeHandler.class),
            @Result(column = "id", property = "diaryId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "spot_id", property = "spotId"),
            @Result(column = "avg_rating", property = "avgRating"),
            @Result(column = "rating_count", property = "ratingCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "update_at", property = "updatedAt")
    })
    Diary getDiaryById(Integer id);

    //根据日记id删除日记
    @Delete("DELETE FROM diary WHERE id = #{id}")
    int deleteDiaryById(Integer id);

    //获取所有日记
    // ordeBy处由于是直接拼接的，有注入风险，要求输入安全
    @Select("""
    SELECT * FROM diary
    ORDER BY
        ${orderBy} DESC
    LIMIT #{limit} OFFSET #{offset}
""")
    @Results({
            @Result(column = "content", property = "content", typeHandler = com.trytry.lasttry.utils.BlobTypeHandler.class),
            @Result(column = "id", property = "diaryId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "spot_id", property = "spotId"),
            @Result(column = "avg_rating", property = "avgRating"),
            @Result(column = "rating_count", property = "ratingCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "update_at", property = "updatedAt")
    })
    List<Diary> getDiaryListPaged(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("orderBy") String orderBy
    );

    //<根据景点编号搜索日记> -> 根据名称搜索日记
    //Todo此算法每页数量与页码未进行规定
    @Select("""
    SELECT * FROM diary WHERE spot_id = #{spotId}
    ORDER BY
        ${orderBy} DESC
    LIMIT #{limit} OFFSET #{offset}
""")
    @Results({
            @Result(column = "content", property = "content", typeHandler = com.trytry.lasttry.utils.BlobTypeHandler.class),
            @Result(column = "id", property = "diaryId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "spot_id", property = "spotId"),
            @Result(column = "avg_rating", property = "avgRating"),
            @Result(column = "rating_count", property = "ratingCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "update_at", property = "updatedAt")
    })
    List<Diary> getDiaryListBySpotIdPaged(@Param("spotId") int spotId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit,
                                        @Param("orderBy") String orderBy
    );

    //根据日记编号得到多篇日记
    @Select({
            "<script>",
            "SELECT * FROM diary WHERE id IN ",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "<if test='orderBy != null'>",
            "ORDER BY ${orderBy} DESC",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "content", property = "content", typeHandler = com.trytry.lasttry.utils.BlobTypeHandler.class),
            @Result(column = "id", property = "diaryId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "spot_id", property = "spotId"),
            @Result(column = "avg_rating", property = "avgRating"),
            @Result(column = "rating_count", property = "ratingCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "update_at", property = "updatedAt")
    })
    List<Diary> getDiariesByIds(@Param("ids") List<Integer> ids,
                                @Param("offset") int offset,
                                @Param("limit") int limit,
                                @Param("orderBy") String orderBy);

    //获取日记当前评分
    @Select("SELECT avg_rating, rating_count FROM diary WHERE id = #{id}")
    Diary getDiaryRating(Integer id);

    //日记评分更新
    @Update("UPDATE diary SET avg_rating = #{avgRating}, rating_count = #{ratingCount} WHERE id = #{id}")
    boolean updateRating(Integer id, Double avgRating, Integer ratingCount);//返回值为更新的行数

    //得到日记总数
    @Select("SELECT COUNT(*) FROM diary")
    int getDiaryCount();

    //从spot表根据名称查询spot_id
    @Select("SELECT spot_id FROM spot WHERE name = #{name}")
    Integer getSpotIdByName(String name);

    //根据spot_id获取name
    @Select("SELECT name FROM spot WHERE spot_id = #{spotId}")
    String getNameBySpotId(Integer spotId);

    //views++
    @Update("UPDATE diary SET views = views + 1 WHERE id = #{id}")
    int increaseViews(Integer id);

    //获得所有的日记，用于添加进入索引
    @Select("SELECT * FROM diary")
    @Results({
            @Result(column = "content", property = "content", typeHandler = com.trytry.lasttry.utils.BlobTypeHandler.class),
            @Result(column = "id", property = "diaryId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "spot_id", property = "spotId"),
            @Result(column = "avg_rating", property = "avgRating"),
            @Result(column = "rating_count", property = "ratingCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "update_at", property = "updatedAt")
    })
    List<Diary> getAllDiary();
}
