package com.trytry.lasttry.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "diary_index")
@Setter
@Getter
@Data
public class DiaryDocument {
    @Id
    private String id;
    private String title;
    private String content;
}
