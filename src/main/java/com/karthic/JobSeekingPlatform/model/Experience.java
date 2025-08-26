package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer experienceId;


    private String organizationName;
    private Date startDate;
    private Date endDate;
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
