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
    public ResponseEntity<QualificationDTO> createQualification(@Valid @PathVariable Long userId, @RequestBody QualificationDTO qualificationDTO) {

        QualificationDTO savedQualificationDTO = qualificationService.createQualification(userId,qualificationDTO);
        return new ResponseEntity<>(savedQualificationDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/qualification/{qualificationId}")
    public ResponseEntity<QualificationDTO> deleteQualification(@PathVariable Long qualificationId){

        QualificationDTO deleteQualification = qualificationService.deleteQualification(qualificationId);
        return new ResponseEntity<>(deleteQualification, HttpStatus.OK);
    }

    @GetMapping("/public/qualifications")
    public ResponseEntity<QualificationResponse> getAllQualifications(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_QUALIFICATION_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder
    ){
        QualificationResponse qualificationResponse = qualificationService.getAllQualifications(pageNumber,pageSize,sortBy,sortOrder, keyword);
        return new ResponseEntity<>(qualificationResponse,HttpStatus.OK);
    }


    @PutMapping("/admin/qualification/{qualificationId}")
    public ResponseEntity<QualificationDTO> updateQualification(@Valid @RequestBody QualificationDTO qualificationDTO,
                                                          @PathVariable Long qualificationId){

        QualificationDTO updatedQualificationDTO = qualificationService.updateQualification(qualificationId,qualificationDTO);
        return new ResponseEntity<>(updatedQualificationDTO,HttpStatus.OK);
    }

    @GetMapping("/public/qualifications/keyword/{keyword}")
    public ResponseEntity<QualificationResponse> getQualificationByKeyword(@PathVariable String keyword,
                                                                     @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                     @RequestParam(name="pageSize", defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                     @RequestParam(name="sortBy", defaultValue= AppConstants.SORT_QUALIFICATION_BY,required = false) String sortBy,
                                                                     @RequestParam(name="sortOrder", defaultValue= AppConstants.SORT_ORDER,required = false) String sortOrder){

        QualificationResponse qualificationResponse = qualificationService.searchQualificationByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(qualificationResponse,HttpStatus.OK);
    }



}
