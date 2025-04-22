package com.trytry.lasttry.utils;

import com.trytry.lasttry.pojo.DiaryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends ElasticsearchRepository<DiaryDocument, Integer> {
    // 自定义查询方法：根据标题或内容模糊搜索
    List<DiaryDocument> findByTitleContainingOrContentContaining(String title, String content);
}
