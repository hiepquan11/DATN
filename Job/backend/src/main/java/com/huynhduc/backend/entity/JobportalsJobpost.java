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
    private int id;  // chuyển sang Long cho nhất quán với bigint trong DB

    @Column(name = "job_name")
    private String title;

    @Column(name = "job_detail")
    private String description;

    @Column(name = "job_requirement", columnDefinition = "longtext")
    private String requirement;

    @Column(name = "benefits_enjoyed", columnDefinition = "longtext")
    private String benefit;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "gender_required")
    private Integer gender;

    @Column(name = "experience_id")
    private Long experienceId;

    @Column(name = "degree_required")
    private String degree;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "is_urgent_job")
    private Integer isUrgentJob;

    @Column(name = "address")
    private String address;

    @Column(name = "probationary_period")
    private String probationaryPeriod;

    @Column(name = "job_description", columnDefinition = "longtext")
    private String jobDescription;

    @Column(name = "request_profile", columnDefinition = "longtext")
    private String requestProfile;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;

    @Column(name = "contact_email")
    private String contactEmail;

    @ManyToOne
    @JoinColumn(name = "career_id")
    @JsonIgnore
    private JobportalsCareer jobportalsCareer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private JobportalsCity jobportalsCity;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @JsonIgnore
    private JobportalsPosition jobportalsPosition;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    @JsonIgnore
    private JobportalsSalary jobportalsSalary;

    @ManyToOne
    @JoinColumn(name = "working_form_id")
    @JsonIgnore
    private JobportalsWorkingform jobportalsWorkingform;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonIgnore
    private JobportalsCompany jobportalsCompany;
}
