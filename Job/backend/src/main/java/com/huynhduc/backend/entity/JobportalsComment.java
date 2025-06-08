package com.huynhduc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "jobportals_comment")
public class JobportalsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private JobportalsCompany companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private JobportalsUser userId;
}
