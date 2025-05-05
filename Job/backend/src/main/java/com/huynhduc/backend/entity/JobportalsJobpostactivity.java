package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobportals_jobpostactivity")
public class JobportalsJobpostactivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "jobpost_id")
    private JobportalsJobpost jobportalsJobpost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private JobportalsUser jobportalsUser;

    @Column(name = "apply_date")
    private LocalDateTime apply_date;

    @Column(name = "status")
    private Integer status;
}
