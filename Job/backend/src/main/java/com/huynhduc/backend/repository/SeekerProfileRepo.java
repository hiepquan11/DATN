package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "seeker-profile")
public interface SeekerProfileRepo extends JpaRepository<JobportalsJobseekerprofile, Integer>
        , JpaSpecificationExecutor<JobportalsJobseekerprofile> {

    @Query("SELECT p FROM JobportalsJobseekerprofile p WHERE p.seeker.id = :id")
    JobportalsJobseekerprofile findBySeekerId(int id);
}
