package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificationsDTO {

    private Integer qualificationId;
    private String degree;
    private Date startDate;
    private Date endDate;
}
