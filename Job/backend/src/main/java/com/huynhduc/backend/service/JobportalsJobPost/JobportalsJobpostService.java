package com.huynhduc.backend.service.JobportalsJobPost;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobportalsJobpostService implements JobportalsJobPostInterface {

    @Autowired
    private JobportalsJobPostRepo repository;

    @Override
    public List<JobportalsJobpost> getAllJobPosts() {
        return repository.findAll();
    }

    @Override
    public JobportalsJobpost getJobPostById(int id) {
        Optional<JobportalsJobpost> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public JobportalsJobpost createJobPost(JobportalsJobpost jobPost) {
        return repository.save(jobPost);
    }

    @Override
    public JobportalsJobpost updateJobPost(int id, JobportalsJobpost jobPost) {
        if (repository.existsById(id)) {
            jobPost.setId(id);
            return repository.save(jobPost);
        }
        return null;
    }

    @Override
    public void deleteJobPost(int id) {
        repository.deleteById(id);
    }
}
