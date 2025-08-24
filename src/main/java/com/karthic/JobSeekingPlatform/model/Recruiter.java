package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Recruiter")
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiter_id")
    private Long recruiterId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "location")
    private String location;

    @OneToOne(mappedBy = "user")
    //@JoinColumn(name = "user_id")
    private User user;



}
