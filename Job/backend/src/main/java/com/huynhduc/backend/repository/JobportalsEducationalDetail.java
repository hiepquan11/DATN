package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsEducationdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "educational-detail")
public interface JobportalsEducationalDetail extends JpaRepository<JobportalsEducationdetail, Integer> {

    @Query("SELECT e FROM JobportalsEducationdetail e WHERE e.seeker.id = :id")
    JobportalsEducationdetail findByUser(int id);
}
