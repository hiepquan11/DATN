package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_experience")
public class JobportalsExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên kinh nghiệm không được để trống")
    @Column(name = "experience_name", nullable = false)
    private String experience_name;
}
