package com.huynhduc.backend.service.JobportalsJobPost;

import com.huynhduc.backend.entity.JobportalsJobpost;
import java.util.List;

public interface JobportalsJobPostInterface {
    List<JobportalsJobpost> getAllJobPosts();
    JobportalsJobpost getJobPostById(int id);
    JobportalsJobpost createJobPost(JobportalsJobpost jobPost);
    JobportalsJobpost updateJobPost(int id, JobportalsJobpost jobPost);
    void deleteJobPost(int id);
}
