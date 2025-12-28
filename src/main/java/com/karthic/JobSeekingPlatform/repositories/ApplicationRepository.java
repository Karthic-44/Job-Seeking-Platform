package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ApplicationRepository extends JpaRepository<Application,Long>,  JpaSpecificationExecutor<Application> {

    Application findByApplicationId(Long applicationId);

    Page<Application> findByApplicationId(Long applicationId, Pageable pageDetails);

    
    
}
