package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import jakarta.validation.Valid;

public interface ExperienceService {
    ExperienceDTO createExperience(@Valid ExperienceDTO experienceDTO);

    ExperienceDTO deleteExperience(Long experienceId);
}
