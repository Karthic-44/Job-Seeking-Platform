package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.config.AppConstants;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.payload.JobResponse;
import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
import com.karthic.JobSeekingPlatform.payload.RecruiterResponse;
import com.karthic.JobSeekingPlatform.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/admin/recruiter/create")
    public ResponseEntity<RecruiterDTO> createRecruiter(@Valid @RequestBody RecruiterDTO recruiterDTO) {

        RecruiterDTO savedRecruiterDTO = recruiterService.createRecruiter(recruiterDTO);
        return new ResponseEntity<>(savedRecruiterDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/recruiter/{recruiterId}")
    public ResponseEntity<RecruiterDTO> deleteRecruiter(@PathVariable Long recruiterId){

        RecruiterDTO deleteRecruiter = recruiterService.deleteRecruiter(recruiterId);
        return new ResponseEntity<>(deleteRecruiter, HttpStatus.OK);
    }

    @GetMapping("/public/recruiters")
    public ResponseEntity<RecruiterResponse> getAllRecruiters(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_RECRUITER_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder
    ){
        RecruiterResponse recruiterResponse = recruiterService.getAllRecruiters(pageNumber,pageSize,sortBy,sortOrder, keyword);
        return new ResponseEntity<>(recruiterResponse,HttpStatus.OK);
    }

    @GetMapping("/public/recruiters/keyword/{keyword}")
    public ResponseEntity<RecruiterResponse> getRecruiterByKeyword(@PathVariable String keyword,
                                                        @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                        @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                        @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_RECRUITER_BY,required = false) String sortBy,
                                                        @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder){

        RecruiterResponse recruiterResponse = recruiterService.searchRecruiterByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(recruiterResponse,HttpStatus.FOUND);
    }


}
