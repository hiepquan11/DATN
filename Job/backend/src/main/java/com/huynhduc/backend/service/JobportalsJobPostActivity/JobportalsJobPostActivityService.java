package com.huynhduc.backend.service.JobportalsJobPostActivity;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.entity.JobportalsJobpostactivity;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsJobPostActivityRepo;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsJobPostActivityService implements JobportalJobPostActivityInterface{

    @Autowired
    private JobportalsJobPostActivityRepo repo;

    @Autowired
    private JobportalsJobPostRepo jobRepo;

    @Autowired
    private JobportalsUserRepo userRepo;

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

    @Override
    public List<JobportalsJobpostactivity> getBySeekerId(int seekerId) {
        JobportalsUser seeker = userRepo.findById(seekerId);
        if (seeker == null) {
            throw new RuntimeException("seeker not found");
        }

        return repo.findBySeekerId(seekerId);
    }

    @Override
    public JobportalsJobpostactivity createJobPostActivity(JobportalsJobpostactivity activity) {
        return repo.save(activity);    }

    @Override
    public boolean hasApplied(int seekerId, int jobPostId) {
        return repo.existsBySeekerIdAndJobPostId(seekerId, jobPostId);    }

    @Override
    public void deleteById(int id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Activity with id " + id + " not found");
        }
        repo.deleteById(id);
    }
}
