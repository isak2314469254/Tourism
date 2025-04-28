package com.trytry.lasttry.pojo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InterestsInfo {
    Integer user_id;
    List<String> interests;
}
