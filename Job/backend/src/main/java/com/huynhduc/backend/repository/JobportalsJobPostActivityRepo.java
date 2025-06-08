package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsJobpostactivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "activity")
public interface JobportalsJobPostActivityRepo extends JpaRepository<JobportalsJobpostactivity, Integer> {

    @Query("SELECT j FROM JobportalsJobpostactivity j WHERE j.jobPost.id = :id")
    List<JobportalsJobpostactivity> findByJobPost(int id);
}
