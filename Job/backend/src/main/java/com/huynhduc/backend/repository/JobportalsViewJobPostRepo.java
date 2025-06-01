package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsViewjobpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "view")
public interface JobportalsViewJobPostRepo extends JpaRepository<JobportalsViewjobpost, Integer> {
    Optional<JobportalsViewjobpost> findByJobportalsJobpost_Id(Integer jobpostId);
}
