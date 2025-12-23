package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Experience;
import com.karthic.JobSeekingPlatform.model.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification,Long> {
    Qualification findByDegree(String degree);

    Page<Qualification> findAll(Specification<Qualification> spec, Pageable pageDetails);

    Page<Qualification> findByDegreeLikeIgnoreCase(String s, Pageable pageDetails);
}
