package com.huynhduc.backend.service.JobportalsViewJobPost;

import com.huynhduc.backend.entity.JobportalsViewjobpost;
import com.huynhduc.backend.repository.JobportalsViewJobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobportalsViewJobPostService implements JobportalsViewJobPostInterface{

    @Autowired
    private JobportalsViewJobPostRepo repo;

    public int GetViewByJobPostId(int jobpostId) {
        return repo.findByJobportalsJobpost_Id(jobpostId)
                .map(JobportalsViewjobpost::getView)
                .orElse(0);
    }
}
