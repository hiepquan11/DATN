package com.huynhduc.backend.service.JobportalsJobPost;

import com.huynhduc.backend.entity.JobportalsJobpost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobportalsJobPostInterface {

    Page<JobportalsJobpost> getJobPostsWithFilters(Map<String, String> filters, Pageable pageable);

    JobportalsJobpost getJobPostById(int id);

    JobportalsJobpost createJobPost(JobportalsJobpost jobPost);

    JobportalsJobpost updateJobPost(int id, JobportalsJobpost jobPost);

    List<JobportalsJobpost> getAllJobPosts();


    void deleteJobPost(int id);
}
