package com.karthic.JobSeekingPlatform.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name="application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @JsonBackReference("user-application")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonBackReference("job-application")
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @JsonBackReference("recruiter-application")
    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiter recruiter;

    private Date appliedDate;
    private String resumeURL;
}
