package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Experience;
import com.karthic.JobSeekingPlatform.model.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {
    Experience findByOrganizationName(String organizationName);

    Page<Experience> findAll(Specification<Experience> spec, Pageable pageDetails);

    Page<Experience> findByOrganizationNameLikeIgnoreCase(String s, Pageable pageDetails);
}
