package com.huynhduc.backend.service.JobportalsJobPostActivity;

import com.huynhduc.backend.entity.JobportalsJobpostactivity;

import java.util.List;

public interface JobportalJobPostActivityInterface {

    JobportalsJobpostactivity getJobPostActivity();
    List<JobportalsJobpostactivity> getJJobPostActivityByJobId(int id);
}
