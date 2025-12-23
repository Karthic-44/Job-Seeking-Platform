package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Experience;
import com.karthic.JobSeekingPlatform.payload.*;
import com.karthic.JobSeekingPlatform.payload.ExperienceDTO;
import com.karthic.JobSeekingPlatform.repositories.ExperienceRepository;
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

    @Override
    public ExperienceDTO deleteExperience(Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience","experienceId",experienceId) );
        experienceRepository.delete(experience);
        return modelMapper.map(experience, ExperienceDTO.class);
    }

    @Override
    public ExperienceResponse getAllExperiences(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Specification<Experience> spec = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("experienceName")), "%"  + keyword.toLowerCase() + "%"));
        }


        Page<Experience> pageExperiences = experienceRepository.findAll(spec, pageDetails);

        List<Experience> experiences = pageExperiences.getContent();

        List<ExperienceDTO> experienceDTOS = experiences.stream()
                .map(experience -> {
                    ExperienceDTO experienceDTO = modelMapper.map(experience, ExperienceDTO.class);

                    return experienceDTO;
                })

                .toList();

        ExperienceResponse experienceResponse = new ExperienceResponse();
        experienceResponse.setContent(experienceDTOS);
        experienceResponse.setPageNumber(pageExperiences.getNumber());
        experienceResponse.setPageSize(pageExperiences.getSize());
        experienceResponse.setTotalElements(pageExperiences.getTotalElements());
        experienceResponse.setTotalPages(pageExperiences.getTotalPages());
        experienceResponse.setLastPage(pageExperiences.isLast());
        return experienceResponse;    }

    @Override
    public ExperienceResponse searchExperienceByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Experience> pageExperiences = experienceRepository.findByOrganizationNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Experience> experiences = pageExperiences.getContent();
        List<ExperienceDTO> experienceDTOS = experiences.stream()
                .map(experience -> modelMapper.map(experience, ExperienceDTO.class))
                .toList();

        if(experiences.isEmpty()){
            throw new APIException("Experiences not found with keyword: " + keyword);
        }

        ExperienceResponse experienceResponse = new ExperienceResponse();
        experienceResponse.setContent(experienceDTOS);
        experienceResponse.setPageNumber(pageExperiences.getNumber());
        experienceResponse.setPageSize(pageExperiences.getSize());
        experienceResponse.setTotalElements(pageExperiences.getTotalElements());
        experienceResponse.setTotalPages(pageExperiences.getTotalPages());
        experienceResponse.setLastPage(pageExperiences.isLast());
        return experienceResponse;    }

    @Override
    public ExperienceDTO updateExperience(Long experienceId, ExperienceDTO experienceDTO) {
        Experience experienceFromDb = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience", "experienceId", experienceId));

        Experience experience = modelMapper.map(experienceDTO, Experience.class);

        experienceFromDb.setOrganizationName(experience.getOrganizationName());
        experienceFromDb.setRole(experience.getRole());
        experienceFromDb.setStartDate(experience.getStartDate());
        experienceFromDb.setEndDate(experience.getEndDate());

        Experience savedExperience = experienceRepository.save(experienceFromDb);

        return modelMapper.map(savedExperience, ExperienceDTO.class);    }
}
