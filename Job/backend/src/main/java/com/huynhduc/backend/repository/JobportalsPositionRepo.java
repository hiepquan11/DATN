package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "position")
public interface JobportalsPositionRepo extends JpaRepository<JobportalsPosition, Integer> {
}
