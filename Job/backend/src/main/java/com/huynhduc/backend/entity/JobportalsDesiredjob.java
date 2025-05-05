package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobportals_desiredjob")
public class JobportalsDesiredjob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private JobportalsUser jobportalsUser;

    @Column(name = "position")
    private String position;

    @Column(name = "career_id")
    private Integer career_id;

    @Column(name = "city_id")
    private Integer city_id;

    @Column(name = "salary_id")
    private Integer salary_id;

    @Column(name = "working_form_id")
    private Integer working_form_id;
}
