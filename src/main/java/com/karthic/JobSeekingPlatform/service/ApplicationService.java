package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.ApplicationDTO;
import com.karthic.JobSeekingPlatform.payload.ApplicationResponse;



public interface ApplicationService {

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO);

    public ApplicationResponse getAllApplications(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,
            String keyword);

    public ApplicationResponse searchApplicationsById(Long applicationId, Integer pageNumber, Integer pageSize,
            String sortBy, String sortOrder);

    public ApplicationDTO deleteApplication(Long applicationId);

    public ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO);
    
}
