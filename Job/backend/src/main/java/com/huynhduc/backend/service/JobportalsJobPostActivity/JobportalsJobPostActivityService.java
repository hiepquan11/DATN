package com.huynhduc.backend.service.JobportalsJobPostActivity;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.entity.JobportalsJobpostactivity;
import com.huynhduc.backend.repository.JobportalsJobPostActivityRepo;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsJobPostActivityService implements JobportalJobPostActivityInterface{

    @Autowired
    private JobportalsJobPostActivityRepo repo;

    @Autowired
    private JobportalsJobPostRepo jobRepo;

    @Override
    public JobportalsJobpostactivity getJobPostActivity() {
        return null;
    }

    @Override
    public List<JobportalsJobpostactivity> getJJobPostActivityByJobId(int id) {
        JobportalsJobpost existJob = jobRepo.findById(id);
        if (existJob == null) {
            throw new RuntimeException("id not found");
        }
        return repo.findByJobPost(id);
    }
}
