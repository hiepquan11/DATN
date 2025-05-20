package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsJobpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "job-post")
public interface JobportalsJobPostRepo extends JpaRepository<JobportalsJobpost, Integer> {
}
