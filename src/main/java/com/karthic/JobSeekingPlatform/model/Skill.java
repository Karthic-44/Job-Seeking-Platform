package com.karthic.JobSeekingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Skill")
public class Skill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="skill-id")
    private  int skillId;

    @Column(name = "skill-name")
    private String skillName;

    @Column(name = "skill-description")
    private String skillDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;



}
