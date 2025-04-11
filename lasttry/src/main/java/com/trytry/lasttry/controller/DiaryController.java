package com.trytry.lasttry.controller;


import com.trytry.lasttry.pojo.*;
import com.trytry.lasttry.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    //发布日志
    @PostMapping("/api/diaries")
    public Result postDiary(@RequestBody DiaryInput diaryInput) {
        try {
            DiaryPublishInfo diaryPublishInfo = new DiaryPublishInfo();
            Integer transSpotId = diaryService.getSpotIdByName(diaryInput.getSpot());
            diaryPublishInfo.setDiary_id(
                    diaryService.publishDiary(
                            diaryInput.getUser_id(),
                            transSpotId,
                            diaryInput.getTitle(),
                            diaryInput.getContent()
                    )
            );
            diaryPublishInfo.setCreatedAt(new Date());

            return Result.success(diaryPublishInfo, "发布日志成功");
        }
        catch (IOException e) {
            log.error("发布日志失败", e);
            return Result.error("发布日志失败");
        }
    }

    //获取日记列表
    @GetMapping("/api/diaries")
    public Result getDiaryList(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sort_by
            ) {
        log.info("获取日志列表");
        List<Diary> diaries = diaryService.getDiaryList(page, size, sort_by);
        int count = diaries.size();
        List<DiarySearchList> diarySearchLists = diaryService.diaryListToViewList(diaries);
        DiarySearchResult diarySearchResult = new DiarySearchResult(count, diarySearchLists);
        return Result.success(diarySearchResult,"获取日志列表成功");
    }

    //根据景点编号搜索日记 -> <根据名称搜索日记>
    //Todo 此处的页码与每页展示没有进行规范
    @GetMapping("/api/diaries/search")
    public Result searchDiaryBySpotId(
            @RequestParam String spot_name,
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam String sort_by
    ) {
        log.info("根据景点编号搜索日记");
        List<Diary> diaries = diaryService.searchDiaryBySpotName(spot_name, offset, limit,
                diaryService.mapOrderBy(sort_by));
        int count = diaries.size();
        List<DiarySearchList> diarySearchLists = diaryService.diaryListToViewList(diaries);
        DiarySearchResult diarySearchResult = new DiarySearchResult(count, diarySearchLists);
        return Result.success(diarySearchResult,"根据景点编号搜索日记成功");
    }

    //获取日志详情
    @GetMapping("/api/diaries/{diary_id}")
    public Result getDiaryDetail(@PathVariable Integer diary_id) throws IOException {

        log.info("获取日志详情");
        DiaryContent diaryContent = diaryService.viewDiary(diary_id);
        return Result.success(diaryContent,"获取日志详情成功");
    }



    //进行日记评分
    @PostMapping("/api/diaries/{diary_id}/rating")
    public Result rateDiary(
            @PathVariable Integer diary_id,
            @RequestBody DiaryRating diaryRating
    ) {
        Double newRating = diaryService.rateDiary(diary_id, diaryRating.getUserRating());
        if (newRating == null  ) {
            return Result.error("评分失败");
        }
        DiaryRatingReturn diaryRatingReturn = new DiaryRatingReturn(newRating);
        diaryRatingReturn.setNewRating(newRating);
        return Result.success(diaryRatingReturn,"评分成功");
    }
}

