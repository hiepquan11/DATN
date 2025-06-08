package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "jobportals_jobpostactivity")
public class JobportalsJobpostactivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_post_id")
    private JobportalsJobpost jobPost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seeker_id")
    private JobportalsUser seeker;

    @Column(name = "apply_date")
    private Date apply_date;

    @Column(name = "status")
    private Boolean status;
}
