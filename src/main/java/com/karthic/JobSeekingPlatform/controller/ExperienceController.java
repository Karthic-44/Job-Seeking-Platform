package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/admin/experience/create")
    public ResponseEntity<ExperienceDTO> createExperience(@Valid @RequestBody ExperienceDTO experienceDTO) {

        ExperienceDTO savedExperienceDTO = experienceService.createExperience(experienceDTO);
        return new ResponseEntity<>(savedExperienceDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/experience/{experienceId}")
    public ResponseEntity<ExperienceDTO> deleteExperience(@PathVariable Long experienceId){

        ExperienceDTO deleteExperience = experienceService.deleteExperience(experienceId);
        return new ResponseEntity<>(deleteExperience, HttpStatus.OK);
    }
}
