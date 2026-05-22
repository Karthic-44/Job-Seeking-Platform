package com.karthic.JobSeekingPlatform.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    
    private Long applicationId;
    @NotNull
    private Long userId;
    @NotNull
    private Long jobId;
    private Date appliedDate;
    private String resumeURL;
}
