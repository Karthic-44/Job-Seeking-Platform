package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.JobDTO;

public interface JobPostingService {

    JobDTO createJob(JobDTO jobDTO);
    JobDTO deleteJob(Long jobId);

}
