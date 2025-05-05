package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_imagecompany")
public class JobportalsImagecompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private JobportalsCompany jobportalsCompany;

    @Column(name = "image_url")
    private String image_url;
}
