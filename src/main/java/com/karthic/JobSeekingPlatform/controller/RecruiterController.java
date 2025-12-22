package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
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


}
