package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobportals_viewcompanyprofile")
public class JobportalsViewcompanyprofile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private JobportalsCompany jobportalsCompany;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private JobportalsUser jobportalsUser;

    @Column(name = "viewed_at")
    private LocalDateTime viewed_at;
}
