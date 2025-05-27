package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "salary")
public interface JobportalsSalaryRepo extends JpaRepository<JobportalsSalary, Integer> {
}
