package com.karthic.JobSeekingPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthic.JobSeekingPlatform.payload.ApplicationDTO;
import com.karthic.JobSeekingPlatform.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @PostMapping("/admin/application/create")
    public ResponseEntity<ApplicationDTO> CreateApplication(@Valid @RequestBody ApplicationDTO applicationDTO) {

        ApplicationDTO savedApplicationDTO = applicationService.createApplication(applicationDTO);
        return new ResponseEntity<>(savedApplicationDTO, HttpStatus.CREATED);
    }

    
    
}
