package com.huynhduc.backend.service.JobportalsExperience;

import com.huynhduc.backend.entity.JobportalsExperience;

import java.util.List;

public interface JobportalsExperienceInterface {

    JobportalsExperience createJobportalsExperience(JobportalsExperience experience);
    List<JobportalsExperience> getAllJobportalsExperiences();
    JobportalsExperience getJobportalsExperienceById(int id);
    JobportalsExperience updateJobportalsExperience(int id, JobportalsExperience updatedExperience);
    void deleteJobportalsExperience(int id);
}
