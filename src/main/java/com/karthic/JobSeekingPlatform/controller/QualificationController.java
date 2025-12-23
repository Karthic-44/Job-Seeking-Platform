package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.config.AppConstants;
import com.karthic.JobSeekingPlatform.payload.QualificationResponse;
import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import com.karthic.JobSeekingPlatform.service.QualificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    @PostMapping("/admin/qualification/create")
    public ResponseEntity<QualificationDTO> createQualification(@Valid @RequestBody QualificationDTO qualificationDTO) {

        QualificationDTO savedQualificationDTO = qualificationService.createQualification(qualificationDTO);
        return new ResponseEntity<>(savedQualificationDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/qualification/{qualificationId}")
    public ResponseEntity<QualificationDTO> deleteQualification(@PathVariable Long qualificationId){

        QualificationDTO deleteQualification = qualificationService.deleteQualification(qualificationId);
        return new ResponseEntity<>(deleteQualification, HttpStatus.OK);
    }


}
