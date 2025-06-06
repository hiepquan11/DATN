package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "jobportals_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobportalsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_joined")
    private Date dateJoined;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userId")
    private List<JobportalsComment> listComments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "jobSeekerId")
    private List<JobportalsJobseekerprofile> listJobSeekerProfiles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "jobportalsUser")
    private List<JobportalsRating> ratingList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "jobportals_user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<JobportalsRole> roles;
}
