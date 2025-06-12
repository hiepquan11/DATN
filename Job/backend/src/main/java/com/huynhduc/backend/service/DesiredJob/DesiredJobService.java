package com.huynhduc.backend.service.DesiredJob;

import com.huynhduc.backend.entity.JobportalsDesiredjob;
import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsDesiredJobRepo;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import com.huynhduc.backend.repository.SeekerProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesiredJobService implements DesiredJobInterface{

    @Autowired
    private JobportalsDesiredJobRepo repo;

    @Autowired
    private JobportalsUserRepo userRepo;

    @Autowired
    private SeekerProfileRepo profileRepo;

    @Override
    public JobportalsDesiredjob findBySeeker(int id) {

        JobportalsUser user = userRepo.findById(id);
        if(user == null){
            throw new RuntimeException("User not found");
        }

        JobportalsJobseekerprofile profile = profileRepo.findBySeekerId(user.getId());
        if(profile == null){
            throw new RuntimeException("Profile not found");
        }

        JobportalsDesiredjob desiredJob = repo.findBySeekerProfile(profile.getId());
        if(desiredJob == null){
            throw new RuntimeException("Desired job not found");
        }

        return desiredJob;
    }
}
