package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    private String userName;

    private String password;

    private String email;

    private Integer userPhoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Skill> skill = new ArrayList<>() ;

    @OneToMany(mappedBy = "user")
    private List<Qualifications> qualification = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Experience> experience = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Recruiter recruiter;

   @OneToOne(mappedBy = "user")
   private Job job;


}
