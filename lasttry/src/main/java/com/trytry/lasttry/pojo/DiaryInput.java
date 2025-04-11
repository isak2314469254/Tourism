package com.trytry.lasttry.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DiaryInput {
    private Integer user_id;
    private String title;
    private String content;
    private String spot;
}
