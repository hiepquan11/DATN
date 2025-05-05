package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_cirriculumvitae")
public class JobportalsCirriculumvitae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url_cv", nullable = false)
    private String cvUrl;


}
