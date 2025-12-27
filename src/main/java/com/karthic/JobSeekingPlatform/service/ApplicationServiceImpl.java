package com.karthic.JobSeekingPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Application;
import com.karthic.JobSeekingPlatform.model.Job;
import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.model.Recruiter;
import com.karthic.JobSeekingPlatform.payload.ApplicationDTO;
import com.karthic.JobSeekingPlatform.repositories.ApplicationRepository;
import com.karthic.JobSeekingPlatform.repositories.JobRepository;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import com.karthic.JobSeekingPlatform.repositories.RecruiterRepository;

import org.modelmapper.ModelMapper;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private UserRepository usersRepository;
    
    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        // Check if application already exists
        if (applicationDTO.getApplicationId() != null) {
            Application existingApp = applicationRepository.findByApplicationId(applicationDTO.getApplicationId());
            if (existingApp != null) {
                throw new APIException("Application " + applicationDTO.getApplicationId() + " already exists");
            }
        }
        
        // Fetch related entities
        Job job = jobRepository.findById(applicationDTO.getJobId())
            .orElseThrow(() -> new ResourceNotFoundException("Job", "jobId", applicationDTO.getJobId()));
            
        Users user = usersRepository.findById(applicationDTO.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", applicationDTO.getUserId()));
        
        // Get recruiter from the job
        Recruiter recruiter = job.getRecruiter();
        if (recruiter == null) {
            throw new APIException("Job " + applicationDTO.getJobId() + " has no associated recruiter");
        }
        
        // Create application entity
        Application application = new Application();
        application.setJob(job);
        application.setUser(user);
        application.setRecruiter(recruiter);
        application.setAppliedDate(applicationDTO.getAppliedDate());
        application.setResumeURL(applicationDTO.getResumeURL());
        
        // Save and return
        Application savedApplication = applicationRepository.save(application);
        return modelMapper.map(savedApplication, ApplicationDTO.class);
    }
}