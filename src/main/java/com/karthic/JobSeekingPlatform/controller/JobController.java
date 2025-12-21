package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobPostingService jobPostingService;


    @PostMapping("/public/job")
    public ResponseEntity<JobDTO> CreateCategories(@Valid @RequestBody JobDTO jobDTO) {

        JobDTO savedJobDTO = jobPostingService.createJob(jobDTO);
        return new ResponseEntity<>(savedJobDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/job/{jobId}")
    public ResponseEntity<JobDTO> deleteCategory(@PathVariable Long jobId){

        JobDTO deleteJob = jobPostingService.deleteJob(jobId);
        return new ResponseEntity<>(deleteJob, HttpStatus.OK);
    }

    @PutMapping("/admin/job/{jobId}")
    public ResponseEntity<JobDTO> updateJob(@Valid @RequestBody JobDTO jobDTO,
                                                    @PathVariable Long jobId){

        JobDTO updatedJobDTO = jobPostingService.updateJob(jobId,jobDTO);
        return new ResponseEntity<>(updatedJobDTO,HttpStatus.OK);
    }

}
