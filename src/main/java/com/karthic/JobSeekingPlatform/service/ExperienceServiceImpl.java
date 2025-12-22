package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.repositories.ExperienceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private ExperienceRepository experienceRepository;
}
