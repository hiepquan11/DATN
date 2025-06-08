package com.huynhduc.backend.service.SeekerProfile;

import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.repository.SeekerProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeekerProfileService implements SeekerProfileInterface{

    @Autowired
    private SeekerProfileRepo repo;

    @Override
    public Page<JobportalsJobseekerprofile> getSeekerProfileWithFilters(Map<String, String> filters, Pageable pageable) {
        Specification<JobportalsJobseekerprofile> spec = (root, query, cb) -> {
            if (query != null) {
                query.orderBy(cb.desc(root.get("created_date")));
            }

            return cb.conjunction();
        };

        return repo.findAll(spec, pageable);
    }
}
