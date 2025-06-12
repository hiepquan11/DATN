package com.huynhduc.backend.service.EducationalDetail;

import com.huynhduc.backend.entity.JobportalsEducationdetail;
import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsEducationalDetail;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import com.huynhduc.backend.repository.SeekerProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationDetailService implements EducationDetailInterface {

    @Autowired
    private JobportalsEducationalDetail repo;

    @Autowired
    private JobportalsUserRepo userRepo;

    @Autowired
    private SeekerProfileRepo profileRepo;

    @Override
    public JobportalsEducationdetail getByUserId(int id) {

        JobportalsUser user = userRepo.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        JobportalsJobseekerprofile profile = profileRepo.findBySeekerId(user.getId());

        if (profile == null) {
            throw new RuntimeException("profile not found");
        }

        JobportalsEducationdetail eduDetail = repo.findByUser(profile.getId());
        if (eduDetail == null) {
            throw new RuntimeException("education detail not found");
        }

        return eduDetail;
    }
}
