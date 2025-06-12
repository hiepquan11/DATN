package com.huynhduc.backend.service.SeekerProfile;

import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SeekerProfileInterface {
    Page<JobportalsJobseekerprofile> getSeekerProfileWithFilters(Map<String, String> filters, Pageable pageable);

    JobportalsJobseekerprofile getJobSeekerProfileById(int id);
}
