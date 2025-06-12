package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "jobportals_educationdetail")
public class JobportalsEducationdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "training_place_name")
    private String training_place_name;

    @Column(name = "major")
    private String major;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "completed_date")
    private Date complete_date;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "description")
    private String description;

    @Column(name = "degree_name")
    private String degree_name;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobportalsJobseekerprofile seeker;
}
