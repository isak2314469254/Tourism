package com.trytry.lasttry.impl;

import com.trytry.lasttry.mapper.DiaryMapper;
import com.trytry.lasttry.mapper.UserMapper;
import com.trytry.lasttry.pojo.Diary;
import com.trytry.lasttry.pojo.DiaryContent;
import com.trytry.lasttry.pojo.DiaryDocument;
import com.trytry.lasttry.pojo.DiarySearchList;
import com.trytry.lasttry.service.DiaryService;
import com.trytry.lasttry.utils.CompressionUtil;
import com.trytry.lasttry.utils.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;
    @Autowired
    private UserMapper userMapperrTemp;
    @Autowired
    private DiaryRepository diaryRepository;

    //发布日志
    @Override
    public Integer publishDiary(Integer userId, Integer spotId, String title, String contentRaw) throws IOException {
        byte[] compressed = CompressionUtil.compress(contentRaw);
        Diary diary = new Diary();
        diary.setUserId(userId);
        diary.setSpotId(spotId);
        diary.setTitle(title);
        diary.setContent(compressed);
        diaryMapper.insertDiary(diary);

        //把日记存入搜索索引
        DiaryDocument diaryDocument = new DiaryDocument();
        diaryDocument.setId(diary.getDiaryId().toString());
        diaryDocument.setTitle(title);
        diaryDocument.setContent(contentRaw);
        diaryRepository.save(diaryDocument);

        return diary.getDiaryId();
    }

    //删除日志
    @Override
    public void deleteDiary(Integer diaryId){
        diaryMapper.deleteDiaryById(diaryId);
    }

    //获取日志内容
    @Override
    public DiaryContent viewDiary(Integer diaryId)throws IOException {
        Diary diary = diaryMapper.getDiaryById(diaryId);
        //解压日记内容
        String conTent = CompressionUtil.decompress(diary.getContent());
        //填充日记详情页数据
        DiaryContent diaryContent = new DiaryContent();
        diaryContent.setDiary_id(diaryId);
        diaryContent.setTitle(diary.getTitle());
        diaryContent.setAuthor(userMapperrTemp.getUsernameById(diary.getUserId()));
        diaryContent.setContent(conTent);
        diaryContent.setHeat(diary.getViews());
        //同时views++
        diaryMapper.increaseViews(diaryId);
        diaryContent.setRating(diary.getAvgRating() != null ? diary.getAvgRating() : 0.0);
        diaryContent.setSpot(diaryMapper.getNameBySpotId(diary.getSpotId()));
        diaryContent.setPublishTime(diary.getCreatedAt());
        return diaryContent;

    }

    //获取日记列表
    @Override
    public List<Diary> getDiaryList(int page, int size, String sortBy){
        return diaryMapper.getDiaryListPaged(page, size, sortBy);
    }

    //根据景点编号搜索日记 -> <根据名称搜索日记>
    public List<Diary> searchDiaryBySpotName(String spot_name, int offset, int limit, String sort_by){
        Integer spotId = diaryMapper.getSpotIdByName(spot_name);
        return diaryMapper.getDiaryListBySpotIdPaged(spotId, offset, limit, sort_by);
    }

    //根据标题或者文本内容搜索日记
    //Todo此算法每页数量与页码未进行规定
    public List<Diary> searchDiaryByTitleOrContent(String keyword, int offset, int limit, String sort_by){
        List<DiaryDocument> diaryDocuments = diaryRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        List<Integer> ids = diaryDocuments.stream()
                .map(diaryDocument -> Integer.parseInt(diaryDocument.getId()))
                .collect(Collectors.toList());
        if (diaryDocuments == null || diaryDocuments.isEmpty()) {
            // 你可以加日志，也可以前端自己判断显示“无结果”
            return Collections.emptyList();
        }
        return diaryMapper.getDiariesByIds(ids, offset, limit, sort_by);
    }

    //日记评分
    @Override
    public Double rateDiary(Integer diaryId, Integer userRating){
        //先获取日记当前评分
        Diary diary = diaryMapper.getDiaryById(diaryId);
        //计算新的平均评分
        Double newRating = (diary.getAvgRating() * diary.getRatingCount() + userRating) / (diary.getRatingCount() + 1);
        //更新数据库
        boolean key = diaryMapper.updateRating(diaryId, newRating, diary.getRatingCount() + 1);
        if(!key) return null;
        return newRating;
//更新成功

    }

    //得到spot_id
    @Override
    public Integer getSpotIdByName(String name){
        return diaryMapper.getSpotIdByName(name);
    }

    @Override
    //数据库日志到展示列表中日志的转换
    public List<DiarySearchList> diaryListToViewList(List<Diary> diaryList){
        return diaryList.stream().map(diary -> {
            DiarySearchList item = new DiarySearchList();
            item.setDiary_id(diary.getDiaryId());
            item.setTitle(diary.getTitle());
            item.setSpot(diaryMapper.getNameBySpotId(diary.getSpotId()));
            item.setHeat(diary.getViews());
            item.setRating(diary.getAvgRating() != null ? diary.getAvgRating() : 0.0);
            item.setPublishTime(diary.getCreatedAt());
            return item;
        }).collect(Collectors.toList());
    }


    //排序字符转换
    public String mapOrderBy(String orderBy) {
        switch (orderBy) {
            case "heat":
                return "views";
            case "rating":
                return "avg_rating";
            case "time":
                return "created_at";
            default:
                throw new IllegalArgumentException("不支持的排序字段：" + orderBy);
        }
    }

    //获得所有的日记
    @Override
    public List<Diary> getAllDiary(){
        return diaryMapper.getAllDiary();
    }

}
