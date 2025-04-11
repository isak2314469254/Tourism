package com.trytry.lasttry.pojo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DiarySearchResult {
    private Integer total;
    private List<DiarySearchList> list;
}
