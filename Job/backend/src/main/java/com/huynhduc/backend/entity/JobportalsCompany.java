package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "jobportals_company")
public class JobportalsCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "field_operation", nullable = false)
    private String fieldOperation;

    @Column(name = "company_size", nullable = false)
    private String companySize;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "tax_id_number")
    private String taxIdNumber;

    @Column(name = "company_website_url")
    private String websiteUrl;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "company_cover_image")
    private String companyCoverImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private JobportalsUser recruiterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private JobportalsCity cityId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "companyId")
    private List<JobportalsComment> comment;
}
