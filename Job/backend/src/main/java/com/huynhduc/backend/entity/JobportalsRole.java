package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "jobportals_role")
public class JobportalsRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String role_name;

    @ManyToMany(mappedBy = "")
    private Set<JobportalsUser> users = new HashSet<>();
}
