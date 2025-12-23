package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import jakarta.validation.Valid;

public interface QualificationService {
    QualificationDTO createQualification(@Valid QualificationDTO qualificationDTO);

    QualificationDTO deleteQualification(Long qualificationId);
}
