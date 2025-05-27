package com.huynhduc.backend.service.JobportalsExperience;

import com.huynhduc.backend.entity.JobportalsExperience;
import com.huynhduc.backend.repository.JobportalsExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsExperienceService implements JobportalsExperienceInterface {

    @Autowired
    private JobportalsExperienceRepo experienceRepo;

    @Override
    public JobportalsExperience createJobportalsExperience(JobportalsExperience experience) {
        return experienceRepo.save(experience);
    }

    @Override
    public List<JobportalsExperience> getAllJobportalsExperiences() {
        List<JobportalsExperience> list = experienceRepo.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("Không có kinh nghiệm nào được tìm thấy.");
        }
        return list;
    }

    @Override
    public JobportalsExperience getJobportalsExperienceById(int id) {
        return experienceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kinh nghiệm với id: " + id));
    }

    @Override
    public JobportalsExperience updateJobportalsExperience(int id, JobportalsExperience updatedExperience) {
        JobportalsExperience existing = experienceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kinh nghiệm với id: " + id));
        existing.setExperience_name(updatedExperience.getExperience_name());
        return experienceRepo.save(existing);
    }

    @Override
    public void deleteJobportalsExperience(int id) {
        if (experienceRepo.existsById(id)) {
            experienceRepo.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy kinh nghiệm với id: " + id);
        }
    }
}
