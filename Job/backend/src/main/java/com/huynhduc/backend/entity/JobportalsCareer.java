package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_career")
public class JobportalsCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "career_name", nullable = false)
    private String career_name;
}
