package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_curriculumvitae")
public class JobportalsCirriculumvitae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url_cv", nullable = false)
    private String cvUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_seeker_profile_id")
    private JobportalsJobseekerprofile profile;
}
