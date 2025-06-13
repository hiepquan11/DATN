package com.huynhduc.backend.endpoint;

public class Endpoint {
    public static final String FRONT_END_HOST = "http://localhost:3000";

    // ====================== PUBLIC ENDPOINTS ======================
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/careers/**",
            "/cities/**",
            "/experiences/**",
            "/salaries/**",
            "/positions/**",
            "/working-forms/**",
            "/top-company/**",
            "/job-posts/**",
            "/companies/**",
            "/oauth2-info",
            "/curriculum-vitae/**",
            "/job-seeker-profiles/**"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/auth/**"
    };

    // ====================== AUTHENTICATED COMMON ENDPOINTS ======================
    public static final String[] AUTHENTICATED_COMMON_ENDPOINTS = {
            "/users/password"
    };

    // ====================== ADMIN ENDPOINTS ======================
    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",  // list all users
            "/companies",  // list all companies
            "/admin/stats/**"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/admin/create-account",
            "/admin/create-company"
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/admin/delete-user/{id}",
            "/admin/delete-company/{id}"
    };

    // ====================== RECRUITER ENDPOINTS ======================
    public static final String[] RECRUITER_GET_ENDPOINTS = {
            "/careers/**",
            "/users/{userId}/company",
            "/users/{id}/job-post",
            "/users/{userId}/job-posts",
            "/users/{userId}/job-posted",
            "/companies/{companyId}/view",
            "/companies/{companyId}/image-companies",
            "/companies/{companyId}/comments",
            "/companies/{companyId}/stats",

    };

    public static final String[] RECRUITER_POST_ENDPOINTS = {
            "/job-posts/",
            "/reply-seeker/",
            "/users/{userId}/job-post",
    };

    public static final String[] RECRUITER_PUT_ENDPOINTS = {
            "/job-posts/{id}"
    };

    public static final String[] RECRUITER_DELETE_ENDPOINTS = {
            "/job-posts/{jobPostId}"
    };

    // ====================== SEEKER ENDPOINTS ======================
    public static final String[] SEEKER_GET_ENDPOINTS = {
            "/users/current-user",
            "/users/{userId}/job-seeker-profile",
            "/users/{userId}/save-job-posts",
            "/job-seeker-profiles/{id}/desired-job",
            "/job-seeker-profiles/{id}/education-detail",
            "/users/{id}/company",
            "/users/{userId}/job-posts-activity",
            "/job-seeker-profiles/{id}/",
            "/job-seeker-profiles/{id}/view",
            "/job-seeker-profiles/{id}/stats",
            "/job-seeker-profiles/{id}/curriculum-vitae",
            "/job-posts/{id}/applied-job-post",
            "/job-seeker-profiles/{userId}/curriculum-vitae",
    };

    public static final String[] SEEKER_POST_ENDPOINTS = {
            "/users/{userId}/save-job-post",
            "/users/{userId}/job-post-activity",
            "/users/{userId}/apply-job-post",
            "/apply-job",
            "/comments/**",
            "/experience-details/"
    };

    public static final String[] SEEKER_PUT_ENDPOINTS = {
            "/experience-details/{experienceDetailId}",
            "/save-job-posts/{saveJobPostId}",
            "/job-posts-activity/{jobPostActivityId}"
    };

    public static final String[] SEEKER_DELETE_ENDPOINTS = {
            "/comments/{commentId}",
            "/save-job-posts/{saveJobPostId}",
            "/job-posts-activity/{jobPostActivityId}"
    };
}
