package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobportals_viewjobpost")
public class JobportalsViewjobpost {
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

    @Column(name = "viewed_at")
    private LocalDateTime viewed_at;
}
