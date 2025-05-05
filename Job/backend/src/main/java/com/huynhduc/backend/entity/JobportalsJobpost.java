package com.huynhduc.backend.entity;

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
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "gender")
    private String gender;

    @Column(name = "experience")
    private String experience;

    @Column(name = "degree")
    private String degree;

    @Column(name = "age")
    private String age;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "created_date")
    private LocalDateTime created_date;

    @Column(name = "updated_date")
    private LocalDateTime updated_date;

    @Column(name = "is_active")
    private Integer is_active;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private JobportalsCompany jobportalsCompany;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private JobportalsPosition jobportalsPosition;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    private JobportalsSalary jobportalsSalary;

    @ManyToOne
    @JoinColumn(name = "working_form_id")
    private JobportalsWorkingform jobportalsWorkingform;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private JobportalsCareer jobportalsCareer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private JobportalsCity jobportalsCity;
}
