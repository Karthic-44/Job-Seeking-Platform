package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.config.AppConstants;
import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.payload.ExperienceResponse;
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

    @GetMapping("/public/experiences")
    public ResponseEntity<ExperienceResponse> getAllExperiences(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_EXPERIENCE_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder
    ){
        ExperienceResponse experienceResponse = experienceService.getAllExperiences(pageNumber,pageSize,sortBy,sortOrder, keyword);
        return new ResponseEntity<>(experienceResponse,HttpStatus.OK);
    }
}
