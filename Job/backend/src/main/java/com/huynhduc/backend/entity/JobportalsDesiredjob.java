package com.huynhduc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huynhduc.backend.DTO.CityDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_desiredjob")
public class JobportalsDesiredjob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "career_id")
    private JobportalsCareer career;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private JobportalsCity city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salary_id")
    private JobportalsSalary salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "working_form_id")
    private JobportalsWorkingform working_form;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_profile_id")
    @JsonIgnore
    private JobportalsJobseekerprofile seeker;
}
