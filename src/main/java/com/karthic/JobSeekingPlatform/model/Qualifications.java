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
@Table(name = "Qualifications")
public class Qualifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Integer qualificationId;


    private String degree;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private Users user;
}
