package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobportals_viewjobseekerprofile")
public class JobportalsViewjobseekerprofile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "seeker_id")
    private JobportalsUser jobportalsSeeker;

    @ManyToOne
    @JoinColumn(name = "viewer_id")
    private JobportalsUser jobportalsViewer;

    @Column(name = "viewed_at")
    private LocalDateTime viewed_at;
}
