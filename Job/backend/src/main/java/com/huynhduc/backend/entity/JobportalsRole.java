package com.huynhduc.backend.entity;

import com.huynhduc.backend.Enum.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "jobportals_role")
@AllArgsConstructor
@NoArgsConstructor
public class JobportalsRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String roleName;
}

