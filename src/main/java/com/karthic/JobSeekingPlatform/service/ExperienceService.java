package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.payload.ExperienceResponse;
import jakarta.validation.Valid;

public interface ExperienceService {
    ExperienceDTO createExperience(@Valid ExperienceDTO experienceDTO);

    ExperienceDTO deleteExperience(Long experienceId);

    ExperienceResponse getAllExperiences(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword);

    ExperienceResponse searchExperienceByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
