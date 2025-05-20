package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "career")
public interface JobportalsCareerRepo extends JpaRepository<JobportalsCareer, Integer> {
}
