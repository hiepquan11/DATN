package com.huynhduc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "jobportals_jobseekerprofile")
@AllArgsConstructor
@NoArgsConstructor
public class JobportalsJobseekerprofile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Date created_date;

    @Column(name = "updated_date")
    private Date updated_date;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "gender")
    private int gender;

    @Column(name = "date_of_birth", nullable = false)
    private Date date_of_birth;

    @Column(name = "marital_status")
    private int marital_status;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "career_goals")
    private String career_goals;

    @Column(name = "personal_skills")
    private String personal_skills;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private JobportalsCity city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_seeker_id")
    private JobportalsUser seeker;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "seeker")
    private JobportalsDesiredjob desired_job;
}
