package com.huynhduc.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "jobportals_user")
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime lastLogin;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userId")
    private List<JobportalsComment> listComments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "jobSeekerId")
    private List<JobportalsJobseekerprofile> listJobSeekerProfiles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "jobportalsUser")
    private List<JobportalsRating> ratingList;

    @ManyToMany
    @JoinTable(
            name = "jobportals_user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<JobportalsRole> roles;

    public Set<JobportalsRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<JobportalsRole> roles) {
        this.roles = roles;
    }

    public List<JobportalsRating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<JobportalsRating> ratingList) {
        this.ratingList = ratingList;
    }

    public List<JobportalsJobseekerprofile> getListJobSeekerProfiles() {
        return listJobSeekerProfiles;
    }

    public void setListJobSeekerProfiles(List<JobportalsJobseekerprofile> listJobSeekerProfiles) {
        this.listJobSeekerProfiles = listJobSeekerProfiles;
    }

    public List<JobportalsComment> getListComments() {
        return listComments;
    }

    public void setListComments(List<JobportalsComment> listComments) {
        this.listComments = listComments;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
