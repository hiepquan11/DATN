package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "role")
public interface JobportalsRoleRepo extends JpaRepository<JobportalsRole, Integer> {

}
