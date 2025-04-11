package com.trytry.lasttry.pojo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiarySearchList {
    private Integer diary_id;
    private String title;
    private String spot;
    private Integer heat;
    private double rating;
    private LocalDateTime publishTime;
}
