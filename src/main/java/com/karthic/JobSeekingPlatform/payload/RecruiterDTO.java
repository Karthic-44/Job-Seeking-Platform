package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterDTO {

    private Long recruiterId;
    private String recruiterName;
    private String email;
    private String password;
    private String location;
}
