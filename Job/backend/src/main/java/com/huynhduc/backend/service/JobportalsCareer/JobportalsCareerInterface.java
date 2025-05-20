package com.huynhduc.backend.service.JobportalsCareer;

import com.huynhduc.backend.entity.JobportalsCareer;
import java.util.List;

public interface JobportalsCareerInterface {
    List<JobportalsCareer> getAllCareers();
    JobportalsCareer getCareerById(int id);
    JobportalsCareer saveCareer(JobportalsCareer career);
    JobportalsCareer updateCareer(int id, JobportalsCareer career);
    void deleteCareer(int id);
}