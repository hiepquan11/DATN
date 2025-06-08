package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "user")
public interface JobportalsUserRepo extends JpaRepository<JobportalsUser, Integer> {
   JobportalsUser findByUsername(String username);
   JobportalsUser findById(int id);
   boolean existsByUsername(String username);
   boolean existsByEmail(String email);

//   List<JobportalsUser> findByJobportalId(int jobportalId);
}
