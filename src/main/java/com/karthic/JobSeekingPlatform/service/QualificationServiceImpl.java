package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Qualification;
import com.karthic.JobSeekingPlatform.model.Qualification;
import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import com.karthic.JobSeekingPlatform.repositories.QualificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualificationServiceImpl implements QualificationService {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private QualificationRepository qualificationRepository;


    @Override
    public QualificationDTO createQualification(QualificationDTO qualificationDTO) {
        Qualification qualification = modelMapper.map(qualificationDTO,Qualification.class);
        Qualification qualificationDb = qualificationRepository.findByDegree(qualification.getDegree());
        if (qualificationDb!=null){
            throw new APIException("Qualification "+ qualification.getDegree() + " already exists");
        }
        Qualification savedUser = qualificationRepository.save(qualification);
        return  modelMapper.map(savedUser, QualificationDTO.class);
    }

    @Override
    public QualificationDTO deleteQualification(Long qualificationId) {
        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Qualification","qualificationId",qualificationId) );
        qualificationRepository.delete(qualification);
        return modelMapper.map(qualification, QualificationDTO.class);
    }


}
