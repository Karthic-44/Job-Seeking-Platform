package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max=50)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max=128)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Size(max=50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max=10)
    @Column(name = "phone")
    private Integer userPhoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Skill> skill;

    @OneToMany(mappedBy = "user")
    private List<qualifications> qualification;

    @OneToMany(mappedBy = "user")
    private List<Experience> experience;

    @OneToOne(mappedBy = "user")
    //@JoinColumn(name = "user_id")
    private Recruiter recruiter;

}
