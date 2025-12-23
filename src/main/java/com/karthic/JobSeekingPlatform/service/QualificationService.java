package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import com.karthic.JobSeekingPlatform.payload.QualificationResponse;
import jakarta.validation.Valid;

public interface QualificationService {
    QualificationDTO createQualification(@Valid QualificationDTO qualificationDTO);

    QualificationDTO deleteQualification(Long qualificationId);

    QualificationResponse getAllQualifications(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword);

    QualificationDTO updateQualification(Long qualificationId, @Valid QualificationDTO qualificationDTO);
}
