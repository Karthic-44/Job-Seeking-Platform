package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    
   private Long applicationId;
    private Long userId;
    private Long jobId;
    private Date appliedDate;
    private String resumeURL;
}
