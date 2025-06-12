package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsCirriculumvitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "cv")
public interface CvRepo extends JpaRepository<JobportalsCirriculumvitae,Integer> {

    @Query("SELECT cv FROM JobportalsCirriculumvitae  cv WHERE cv.profile.id = :id")
    List<JobportalsCirriculumvitae> findBySeekerProfile(int id);

}
