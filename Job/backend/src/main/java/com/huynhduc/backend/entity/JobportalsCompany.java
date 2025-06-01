package com.huynhduc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // Giữ nguyên đúng tên cột
    @Column(name = "company_name", nullable = false)
    private String company_name;

    @Column(name = "field_operation", nullable = false)
    private String field_operation;

    @Column(name = "company_size", nullable = false)
    private String company_size;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "tax_id_number")
    private String tax_id_number;

    @Column(name = "company_website_url")
    private String company_website_url;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "company_description")
    private String company_description;

    @Column(name = "company_cover_image")
    private String company_cover_image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private JobportalsUser recruiter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private JobportalsCity city;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "companyId")
    private List<JobportalsComment> comment;
}
