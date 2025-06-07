package com.huynhduc.backend.service.JobportalsJobPost;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

import java.util.*;

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

        jobPost.setCreated_date(new Date());
        jobPost.setUpdated_date(new Date());

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

            // filter by keyword (job name)
            String keyword = filters.get("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("job_name")), "%" + keyword.trim().toLowerCase() + "%"));
            }

            // filter by city
            String city = filters.get("city");
            if (isNumeric(city)) {
                predicates.add(cb.equal(root.get("city").get("id"), Integer.parseInt(city)));
            }

            // filter by experience
            String experience = filters.get("experience");
            if (isNumeric(experience)) {
                predicates.add(cb.equal(root.get("experience").get("id"), Integer.parseInt(experience)));
            }

            // filter by salary
            String salary = filters.get("salary");
            if (isNumeric(salary)) {
                predicates.add(cb.equal(root.get("salary").get("id"), Integer.parseInt(salary)));
            }

            // filter by position
            String position = filters.get("position");
            if (isNumeric(position)) {
                predicates.add(cb.equal(root.get("position").get("id"), Integer.parseInt(position)));
            }

            // filter by working_form
            String workingForm = filters.get("working_form");
            if (isNumeric(workingForm)) {
                predicates.add(cb.equal(root.get("working_form").get("id"), Integer.parseInt(workingForm)));
            }

            //filter by career
            String career = filters.get("career");
            if (isNumeric(career)) {
                predicates.add(cb.equal(root.get("career").get("id"), Integer.parseInt(career)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable);
    }

    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) return false;
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
