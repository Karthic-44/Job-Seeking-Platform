package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Job;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import org.modelmapper.ModelMapper;
import com.karthic.JobSeekingPlatform.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class JobPostingServiceImpl implements JobPostingService{

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobDTO createJob(JobDTO jobDTO){
        Job job = modelMapper.map(jobDTO,Job.class);
        Job jobDb = jobRepository.findByJobName(job.getJobName());
        if (jobDb!=null){
            throw new APIException("Job "+ job.getJobName() + " already exists");
        }
        Job savedJob = jobRepository.save(job);
        return  modelMapper.map(savedJob, JobDTO.class);
    }

    @Override
    public JobDTO deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job","jobId",jobId) );
        jobRepository.delete(job);
        return modelMapper.map(job,JobDTO.class);
    }

    @Override
    public JobDTO updateJob(Long jobId, JobDTO jobDTO) {
        Job jobFromDb = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "jobId", jobId));

        Job job = modelMapper.map(jobDTO, Job.class);

        jobFromDb.setJobName(job.getJobName());
        jobFromDb.setRole(job.getRole());
        jobFromDb.setRequiredSkill(job.getRequiredSkill());
        jobFromDb.setRequiredQualifications(job.getRequiredQualifications());
        jobFromDb.setDescription(job.getDescription());


        Job savedJob = jobRepository.save(jobFromDb);

        return modelMapper.map(savedJob, JobDTO.class);    }
}
