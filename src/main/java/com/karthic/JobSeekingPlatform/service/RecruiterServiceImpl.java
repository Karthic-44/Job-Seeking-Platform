package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.repositories.RecruiterRepository;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruiterServiceImpl implements RecruiterService{

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private RecruiterRepository recruiterRepository;

}
