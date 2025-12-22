package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
import com.karthic.JobSeekingPlatform.payload.RecruiterResponse;
import jakarta.validation.Valid;

public interface RecruiterService {
    RecruiterDTO createRecruiter(@Valid RecruiterDTO recruiterDTO);

    RecruiterDTO deleteRecruiter(Long recruiterId);

    RecruiterResponse getAllRecruiters(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword);
}
