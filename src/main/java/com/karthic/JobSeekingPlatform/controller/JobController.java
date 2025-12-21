package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.config.AppConstants;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.payload.JobResponse;
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


    @GetMapping("/public/jobs")
    public ResponseEntity<JobResponse> getAllJobs(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_JOB_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder
    ){
        JobResponse jobResponse = jobPostingService.getAllJobs(pageNumber,pageSize,sortBy,sortOrder, keyword);
        return new ResponseEntity<>(jobResponse,HttpStatus.OK);
    }

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
