package com.trytry.lasttry.pojo;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiaryPublishInfo {
    private Integer diary_id;
    private Date createdAt;
}
