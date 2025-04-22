package com.trytry.lasttry.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class InitialUtil {
    @Autowired
    private DiartRepositoryUtil diartRepositoryUtil;

    @GetMapping("/sync")
    public String syncAllDiariesToElasticsearch() {
        diartRepositoryUtil.syncAllDiariesToElasticsearch();
        return "所有日记同步至 Elasticsearch 成功！";
    }
}
