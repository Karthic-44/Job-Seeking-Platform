package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Recruiter;
import com.karthic.JobSeekingPlatform.payload.RecruiterDTO;
import com.karthic.JobSeekingPlatform.repositories.RecruiterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
