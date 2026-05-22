package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Qualifications")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Integer qualificationId;


    private String degree;
    private String institution;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private Users user;
}
