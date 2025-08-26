package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDTO {

    private Integer experienceId;
    private String organizationName;
    private Date startDate;
    private Date endDate;
    private String role;

}
