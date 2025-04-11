package com.trytry.lasttry.service;

import com.trytry.lasttry.pojo.Diary;
import com.trytry.lasttry.pojo.DiaryContent;
import com.trytry.lasttry.pojo.DiarySearchList;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DiaryService {

    //发布日志
    Integer publishDiary(Integer userId, Integer spotId, String title, String contentRaw) throws IOException ;

    //删除日志
    void deleteDiary(Integer diaryId);

    //获取日志详情
    DiaryContent viewDiary(Integer id) throws IOException;

    //获取日志列表
    List<Diary> getDiaryList(int page, int size, String sortBy);

    //根据景点编号搜索日记 -> <根据名称搜索日记>
    //Todo此算法每页数量与页码未进行规定
    List<Diary> searchDiaryBySpotName(String spot_name, int offset, int limit, String sort_by);

    //根据标题或者文本内容搜索日记
    //Todo此算法每页数量与页码未进行规定
    List<Diary> searchDiaryByTitleOrContent(String keyword, int offset, int limit, String sort_by);

    //日记评分,1为更新成功，0为失败
    Double rateDiary(Integer diaryId, Integer userRating);

    //数据库日志到展示列表中日志的转换
    List<DiarySearchList> diaryListToViewList(List<Diary> diaryList);

    //从spot的名字得到id
    Integer getSpotIdByName(String spotName);

    //排序字符转换
    public String mapOrderBy(String orderBy);

//    //进行日记评分
//    void rateDiary(Integer diaryId, Integer userId, Integer score);
}
