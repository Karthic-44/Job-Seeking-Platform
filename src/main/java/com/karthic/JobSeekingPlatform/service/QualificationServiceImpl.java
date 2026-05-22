package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Qualification;
import com.karthic.JobSeekingPlatform.model.Qualification;
import com.karthic.JobSeekingPlatform.model.Qualification;
import com.karthic.JobSeekingPlatform.payload.*;
import com.karthic.JobSeekingPlatform.payload.QualificationDTO;
import com.karthic.JobSeekingPlatform.repositories.QualificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public QualificationResponse getAllQualifications(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Specification<Qualification> spec = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("degree")), "%"  + keyword.toLowerCase() + "%"));
        }


        Page<Qualification> pageQualifications = qualificationRepository.findAll(spec, pageDetails);

        List<Qualification> qualifications = pageQualifications.getContent();

        List<QualificationDTO> qualificationDTOS = qualifications.stream()
                .map(qualification -> {
                    QualificationDTO qualificationDTO = modelMapper.map(qualification, QualificationDTO.class);

                    return qualificationDTO;
                })

                .toList();

        QualificationResponse qualificationResponse = new QualificationResponse();
        qualificationResponse.setContent(qualificationDTOS);
        qualificationResponse.setPageNumber(pageQualifications.getNumber());
        qualificationResponse.setPageSize(pageQualifications.getSize());
        qualificationResponse.setTotalElements(pageQualifications.getTotalElements());
        qualificationResponse.setTotalPages(pageQualifications.getTotalPages());
        qualificationResponse.setLastPage(pageQualifications.isLast());
        return qualificationResponse;      }

    @Override
    public QualificationDTO updateQualification(Long qualificationId, QualificationDTO qualificationDTO) {
        Qualification qualificationFromDb = qualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Qualification", "qualificationId", qualificationId));

        Qualification qualification = modelMapper.map(qualificationDTO, Qualification.class);

        qualificationFromDb.setDegree(qualification.getDegree());
        qualificationFromDb.setInstitution(qualification.getInstitution());
        qualificationFromDb.setStartDate(qualification.getStartDate());
        qualificationFromDb.setEndDate(qualification.getEndDate());

        Qualification savedQualification = qualificationRepository.save(qualificationFromDb);

        return modelMapper.map(savedQualification, QualificationDTO.class);     }

    @Override
    public QualificationResponse searchQualificationByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Qualification> pageQualifications = qualificationRepository.findByDegreeLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Qualification> qualifications = pageQualifications.getContent();
        List<QualificationDTO> qualificationDTOS = qualifications.stream()
                .map(qualification -> modelMapper.map(qualification, QualificationDTO.class))
                .toList();

        if(qualifications.isEmpty()){
            throw new APIException("Qualifications not found with keyword: " + keyword);
        }

        QualificationResponse qualificationResponse = new QualificationResponse();
        qualificationResponse.setContent(qualificationDTOS);
        qualificationResponse.setPageNumber(pageQualifications.getNumber());
        qualificationResponse.setPageSize(pageQualifications.getSize());
        qualificationResponse.setTotalElements(pageQualifications.getTotalElements());
        qualificationResponse.setTotalPages(pageQualifications.getTotalPages());
        qualificationResponse.setLastPage(pageQualifications.isLast());
        return qualificationResponse;      }


}
