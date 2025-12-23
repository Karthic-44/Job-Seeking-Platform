package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.model.Experience;
import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
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

    @Override
    public ExperienceDTO createExperience(ExperienceDTO experienceDTO) {
        Experience experience = modelMapper.map(experienceDTO,Experience.class);
        Experience experienceDb = experienceRepository.findByOrganizationName(experience.getOrganizationName());
        if (experienceDb!=null){
            throw new APIException("Experience "+ experience.getOrganizationName() + " already exists");
        }
        Experience savedUser = experienceRepository.save(experience);
        return  modelMapper.map(savedUser, ExperienceDTO.class);
    }
}
