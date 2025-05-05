package com.huynhduc.backend.entity;

import jakarta.persistence.*;

public class JobportalsUserGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private JobportalsUser jobportalsUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private JobportalsRole jobportalsRole;
}
