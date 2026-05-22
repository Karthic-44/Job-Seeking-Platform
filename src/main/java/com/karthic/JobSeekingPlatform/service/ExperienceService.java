package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.payload.ExperienceResponse;
import jakarta.validation.Valid;

public interface ExperienceService {
    ExperienceDTO createExperience(@Valid Long userId, ExperienceDTO experienceDTO);

    ExperienceDTO deleteExperience(Long experienceId);

    ExperienceResponse getAllExperiences(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword);

    ExperienceResponse searchExperienceByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ExperienceDTO updateExperience(Long experienceId, @Valid ExperienceDTO experienceDTO);
}
