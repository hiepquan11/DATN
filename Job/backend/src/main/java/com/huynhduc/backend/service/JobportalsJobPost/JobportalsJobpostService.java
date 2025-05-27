package com.huynhduc.backend.service.JobportalsJobPost;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Override
    public Page<JobportalsJobpost> getJobPostsWithFilters(Map<String, String> filters, Pageable pageable) {
        Specification<JobportalsJobpost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.containsKey("keyword")) {
                String keyword = filters.get("keyword");
                if (keyword != null && !keyword.trim().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("jobName")), "%" + keyword.toLowerCase() + "%"));
                }
            }

            if (filters.containsKey("city")) {
                try {
                    int cityId = Integer.parseInt(filters.get("city"));
                    predicates.add(cb.equal(root.get("city").get("id"), cityId));
                } catch (NumberFormatException ignored) {
                }
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable);
    }
}
