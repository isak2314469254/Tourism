package com.trytry.lasttry.utils;


import com.trytry.lasttry.mapper.DiaryMapper;
import com.trytry.lasttry.pojo.Diary;
import com.trytry.lasttry.pojo.DiaryDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//用于初始化数据库里的所有日记文件到索引
@Slf4j
@Service
public class DiartRepositoryUtil {

    @Autowired
    DiaryMapper diaryMapper;

    @Autowired
    private DiaryRepository diaryRepository;

    public void syncAllDiariesToElasticsearch()  {
        List<Diary> diaryList = diaryMapper.getAllDiary();
        List<DiaryDocument> documentList = diaryList.stream().map(diary -> {
            DiaryDocument doc = new DiaryDocument();
            doc.setId(diary.getDiaryId().toString());
            doc.setTitle(diary.getTitle());
            try{
                doc.setContent(CompressionUtil.decompress(diary.getContent()));
            }
            catch (IOException e){
                log.warn("解压日记失败，diaryId: {}", diary.getDiaryId(), e);
                doc.setContent(""); // 设置为空或其他默认值
            }
            return doc;
        }).collect(Collectors.toList());
        diaryRepository.saveAll(documentList);
    }
}
