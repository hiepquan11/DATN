package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "experience")
public interface JobportalsExperienceRepo extends JpaRepository<JobportalsExperience, Integer> {
}
