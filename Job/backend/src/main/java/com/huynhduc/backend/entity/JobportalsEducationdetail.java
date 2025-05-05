package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "jobportals_educationdetail")
public class JobportalsEducationdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "school_name")
    private String school_name;

    @Column(name = "major")
    private String major;

    @Column(name = "start_year")
    private Integer start_year;

    @Column(name = "end_year")
    private Integer end_year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private JobportalsUser jobportalsUser;
}
