package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter,Long> {
    Recruiter findByRecruiterName(String recruiterName);

    Page<Recruiter> findAll(Specification<Recruiter> spec, Pageable pageDetails);

    Page<Recruiter> findByRecruiterNameLikeIgnoreCase(String s, Pageable pageDetails);
}
