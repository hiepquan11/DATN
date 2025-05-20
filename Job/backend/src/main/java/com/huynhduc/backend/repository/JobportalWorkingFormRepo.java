package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsWorkingform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "working-form")
public interface JobportalWorkingFormRepo extends JpaRepository<JobportalsWorkingform, Integer> {
}
