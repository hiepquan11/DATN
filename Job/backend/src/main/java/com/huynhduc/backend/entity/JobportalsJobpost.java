package com.huynhduc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobportals_jobpost")
public class JobportalsJobpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "job_name")
    private String job_name;

    @Column(name = "job_detail")
    private String job_detail;

    @Column(name = "job_requirement", columnDefinition = "longtext")
    private String job_requirement;

    @Column(name = "benefits_enjoyed", columnDefinition = "longtext")
    private String benefits_enjoyed;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "gender_required")
    private Integer gender_required;

    @Column(name = "degree_required")
    private String degree_required;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "created_date")
    private LocalDateTime created_date;

    @Column(name = "updated_date")
    private LocalDateTime updated_date;

    @Column(name = "is_active")
    private Integer is_active;

    @Column(name = "is_urgent_job")
    private Integer is_urgent_job;

    @Column(name = "address")
    private String address;

    @Column(name = "probationary_period")
    private String probationary_period;

    @Column(name = "job_description", columnDefinition = "longtext")
    private String job_description;

    @Column(name = "request_profile", columnDefinition = "longtext")
    private String request_profile;

    @Column(name = "contact_person_name")
    private String contact_person_name;

    @Column(name = "contact_address")
    private String contact_address;

    @Column(name = "contact_phone_number")
    private String contact_phone_number;

    @Column(name = "contact_email")
    private String contact_email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "career_id")
    private JobportalsCareer career;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private JobportalsCity city;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private JobportalsPosition position;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    private JobportalsSalary salary;

    @ManyToOne
    @JoinColumn(name = "working_form_id")
    private JobportalsWorkingform working_form;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private JobportalsCompany recruiter;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private JobportalsExperience experience;
}
