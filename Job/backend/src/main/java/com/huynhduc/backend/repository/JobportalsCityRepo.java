package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "city")
public interface JobportalsCityRepo extends JpaRepository<JobportalsCity, Integer> {
}
