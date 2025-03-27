package com.trytry.lasttry.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginInfo {
    private Integer user_id;
    private String token;
}
