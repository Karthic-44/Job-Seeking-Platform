package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
import jakarta.validation.Valid;

public interface RecruiterService {
    RecruiterDTO createRecruiter(@Valid RecruiterDTO recruiterDTO);

    RecruiterDTO deleteRecruiter(Long recruiterId);
}
