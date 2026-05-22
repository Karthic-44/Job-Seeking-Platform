package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String userPhoneNumber;

    @ElementCollection
    private List<String> skills;

    @OneToMany(mappedBy = "user")
    private List<Qualification> qualification = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Experience> experience = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Recruiter recruiter;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
}
