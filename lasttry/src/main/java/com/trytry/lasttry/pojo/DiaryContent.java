package com.trytry.lasttry.pojo;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DiaryContent {
    private Integer diary_id;
    private String title;
    private String author;
    private String content;
    private Integer heat;
    private double rating;
    private String spot;
    private LocalDateTime publishTime;
}
