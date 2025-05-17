package com.huynhduc.backend.repository;

import com.huynhduc.backend.Enum.ERole;
import com.huynhduc.backend.entity.JobportalsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.management.relation.Role;
import java.util.Optional;

@RepositoryRestResource(path = "role")
public interface JobportalsRoleRepo extends JpaRepository<JobportalsRole, Integer> {
    Optional<JobportalsRole> findByRoleName(String roleName);
}
