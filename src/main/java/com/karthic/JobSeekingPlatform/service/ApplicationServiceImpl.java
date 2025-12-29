package com.karthic.JobSeekingPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Application;
import com.karthic.JobSeekingPlatform.model.Job;
import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.model.Recruiter;
import com.karthic.JobSeekingPlatform.payload.ApplicationDTO;
import com.karthic.JobSeekingPlatform.payload.ApplicationResponse;

import com.karthic.JobSeekingPlatform.repositories.ApplicationRepository;
import com.karthic.JobSeekingPlatform.repositories.JobRepository;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import com.karthic.JobSeekingPlatform.repositories.RecruiterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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
        if (applicationDTO.getApplicationId() != null) {
            Application existingApp = applicationRepository.findByApplicationId(applicationDTO.getApplicationId());
            if (existingApp != null) {
                throw new APIException("Application " + applicationDTO.getApplicationId() + " already exists");
            }
        }
        
        Job job = jobRepository.findById(applicationDTO.getJobId())
            .orElseThrow(() -> new ResourceNotFoundException("Job", "jobId", applicationDTO.getJobId()));
            
        Users user = usersRepository.findById(applicationDTO.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", applicationDTO.getUserId()));
        
        Recruiter recruiter = job.getRecruiter();
        if (recruiter == null) {
            throw new APIException("Job " + applicationDTO.getJobId() + " has no associated recruiter");
        }
        
        Application application = new Application();
        application.setJob(job);
        application.setUser(user);
        application.setRecruiter(recruiter);
        application.setAppliedDate(applicationDTO.getAppliedDate());
        application.setResumeURL(applicationDTO.getResumeURL());
        
        Application savedApplication = applicationRepository.save(application);
        return modelMapper.map(savedApplication, ApplicationDTO.class);
    }

    @Override
    public ApplicationResponse getAllApplications(Long userId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder, String keyword) {
            Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    
    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

    Specification<Application> spec = Specification.where(null);
    
    spec = spec.and((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("user").get("userId"), userId));
    
    if (keyword != null && !keyword.isEmpty()) {
        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("job").get("jobName")), 
                        "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("job").get("role")), 
                        "%" + keyword.toLowerCase() + "%")
                ));
    }

    Page<Application> pageApplications = applicationRepository.findAll(spec, pageDetails);
    List<Application> applications = pageApplications.getContent();

    List<ApplicationDTO> applicationDTOS = applications.stream()
            .map(application -> modelMapper.map(application, ApplicationDTO.class))
            .toList();

    ApplicationResponse applicationResponse = new ApplicationResponse();
    applicationResponse.setContent(applicationDTOS);
    applicationResponse.setPageNumber(pageApplications.getNumber());
    applicationResponse.setPageSize(pageApplications.getSize());
    applicationResponse.setTotalElements(pageApplications.getTotalElements());
    applicationResponse.setTotalPages(pageApplications.getTotalPages());
    applicationResponse.setLastPage(pageApplications.isLast());
    
    return applicationResponse;
}

    @Override
    public ApplicationResponse searchApplicationsById(Long applicationId, Integer pageNumber, Integer pageSize,
            String sortBy, String sortOrder) {
               
Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Application> pageApplication = applicationRepository.findByApplicationId(applicationId, pageDetails);

        List<Application> applications = pageApplication.getContent();
        List<ApplicationDTO> applicationsDTOS = applications.stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();

        if(applications.isEmpty()){
            throw new APIException("Application not found with id: " + applicationId);
        }

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setContent(applicationsDTOS);
        applicationResponse.setPageNumber(pageApplication.getNumber());
        applicationResponse.setPageSize(pageApplication.getSize());
        applicationResponse.setTotalElements(pageApplication.getTotalElements());
        applicationResponse.setTotalPages(pageApplication.getTotalPages());
        applicationResponse.setLastPage(pageApplication.isLast());
        return applicationResponse;            }


    @Override
    public ApplicationDTO deleteApplication(Long applicationId) {

    Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application","applicationId",applicationId) );

        applicationRepository.delete(application);
        return modelMapper.map(application,ApplicationDTO.class);    }
    


}
