package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String userPhoneNumber;
    private List<String> skills;


}
