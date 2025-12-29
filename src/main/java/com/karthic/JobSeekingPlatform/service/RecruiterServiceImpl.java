package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Recruiter;
import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
import com.karthic.JobSeekingPlatform.payload.RecruiterResponse;
import com.karthic.JobSeekingPlatform.repositories.RecruiterRepository;
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
public class RecruiterServiceImpl implements RecruiterService{

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public RecruiterDTO createRecruiter(RecruiterDTO recruiterDTO) {
        Recruiter recruiter = modelMapper.map(recruiterDTO,Recruiter.class);
        Recruiter recruiterDb = recruiterRepository.findByRecruiterName(recruiter.getRecruiterName());
        if (recruiterDb!=null){
            throw new APIException("Recruiter "+ recruiter.getRecruiterName() + " already exists");
        }
        Recruiter savedUser = recruiterRepository.save(recruiter);
        return  modelMapper.map(savedUser, RecruiterDTO.class);
    }

    @Override
    public RecruiterDTO deleteRecruiter(Long recruiterId) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter","recruiterId",recruiterId) );
        recruiterRepository.delete(recruiter);
        return modelMapper.map(recruiter, RecruiterDTO.class);
    }

    @Override
    public RecruiterResponse getAllRecruiters(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Specification<Recruiter> spec = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("recruiterName")), "%"  + keyword.toLowerCase() + "%"));
        }


        Page<Recruiter> pageRecruiters = recruiterRepository.findAll(spec, pageDetails);

        List<Recruiter> recruiters = pageRecruiters.getContent();

        List<RecruiterDTO> recruiterDTOS = recruiters.stream()
                .map(recruiter -> {
                    RecruiterDTO recruiterDTO = modelMapper.map(recruiter, RecruiterDTO.class);

                    return recruiterDTO;
                })

                .toList();

        RecruiterResponse recruiterResponse = new RecruiterResponse();
        recruiterResponse.setContent(recruiterDTOS);
        recruiterResponse.setPageNumber(pageRecruiters.getNumber());
        recruiterResponse.setPageSize(pageRecruiters.getSize());
        recruiterResponse.setTotalElements(pageRecruiters.getTotalElements());
        recruiterResponse.setTotalPages(pageRecruiters.getTotalPages());
        recruiterResponse.setLastPage(pageRecruiters.isLast());
        return recruiterResponse;
    }

    @Override
    public RecruiterResponse searchRecruiterByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Recruiter> pageRecruiters = recruiterRepository.findByRecruiterNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Recruiter> recruiters = pageRecruiters.getContent();
        List<RecruiterDTO> recruiterDTOS = recruiters.stream()
                .map(recruiter -> modelMapper.map(recruiter, RecruiterDTO.class))
                .toList();

        if(recruiters.isEmpty()){
            throw new APIException("Recruiters not found with keyword: " + keyword);
        }

        RecruiterResponse recruiterResponse = new RecruiterResponse();
        recruiterResponse.setContent(recruiterDTOS);
        recruiterResponse.setPageNumber(pageRecruiters.getNumber());
        recruiterResponse.setPageSize(pageRecruiters.getSize());
        recruiterResponse.setTotalElements(pageRecruiters.getTotalElements());
        recruiterResponse.setTotalPages(pageRecruiters.getTotalPages());
        recruiterResponse.setLastPage(pageRecruiters.isLast());
        return recruiterResponse;
    }

    @Override
    public RecruiterDTO updateRecruiter(Long recruiterId, RecruiterDTO recruiterDTO) {
        Recruiter recruiterFromDb = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter", "recruiterId", recruiterId));

        Recruiter recruiter = modelMapper.map(recruiterDTO, Recruiter.class);

        recruiterFromDb.setRecruiterName(recruiter.getRecruiterName());
        recruiterFromDb.setEmail(recruiter.getEmail());
        recruiterFromDb.setPassword(recruiter.getPassword());
        recruiterFromDb.setLocation(recruiter.getLocation());

        Recruiter savedRecruiter = recruiterRepository.save(recruiterFromDb);

        return modelMapper.map(savedRecruiter, RecruiterDTO.class);
    }
}
