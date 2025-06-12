package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsDesiredjob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "desired-job")
public interface JobportalsDesiredJobRepo extends JpaRepository<JobportalsDesiredjob, Integer> {

    @Query("SELECT d FROM JobportalsDesiredjob d WHERE d.seeker.id = :id")
    JobportalsDesiredjob findBySeekerProfile(int id);
}
