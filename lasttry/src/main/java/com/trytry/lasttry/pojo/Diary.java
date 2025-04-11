package com.trytry.lasttry.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Diary {
    private Integer diaryId;
    private Integer userId;
    private Integer spotId;
    private String title;
    private byte[] content; // 压缩后的内容
    private Integer views;
    private Double avgRating;
    private Integer ratingCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
