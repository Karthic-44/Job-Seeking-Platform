package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Job;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job,Long>,
        JpaSpecificationExecutor<Job> {

    Job findByJobName(String jobName);

}
