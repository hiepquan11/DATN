package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.entity.JobportalsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "job-post")
public interface JobportalsJobPostRepo extends JpaRepository<JobportalsJobpost, Integer>,
        JpaSpecificationExecutor<JobportalsJobpost> {

    @Query("SELECT j FROM JobportalsJobpost j WHERE j.recruiter.id = :recruiterId ORDER BY j.created_date DESC ")
    List<JobportalsJobpost> findByRecruiterIdOrderByCreatedDate(int recruiterId);

    JobportalsJobpost findById(int id);
}
