package com.trytry.lasttry.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
    @JsonProperty("tag_ids")//适配前端
    private List<Integer> tagIds;
}
