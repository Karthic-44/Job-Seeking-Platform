package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Recruiter")
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiter_id")
    private Long recruiterId;
    private String recruiterName;
    private String email;
    private String password;
    private String location;


    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "recruiter")
    private List<Job> job = new ArrayList<>();


    @JsonManagedReference("recruiter-application")
    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();


}
