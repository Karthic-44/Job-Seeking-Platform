package com.karthic.JobSeekingPlatform.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @NotBlank
    @Size(min=5, message = "Name must contain atleast 5 characters  ")
    private String jobName;

    private String role;
    private String requiredSkill;
    private String requiredQualifications;
    private String description;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonManagedReference("job-application")
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();




}
