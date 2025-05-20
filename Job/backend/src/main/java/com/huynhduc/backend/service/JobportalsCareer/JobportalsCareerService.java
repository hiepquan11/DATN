package com.huynhduc.backend.service.JobportalsCareer;

import com.huynhduc.backend.entity.JobportalsCareer;
import com.huynhduc.backend.repository.JobportalsCareerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobportalsCareerService implements JobportalsCareerInterface {

    @Autowired
    private JobportalsCareerRepo repository;

    @Override
    public List<JobportalsCareer> getAllCareers() {
        return repository.findAll();
    }

    @Override
    public JobportalsCareer getCareerById(int id) {
        Optional<JobportalsCareer> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public JobportalsCareer saveCareer(JobportalsCareer career) {
        return repository.save(career);
    }

    @Override
    public JobportalsCareer updateCareer(int id, JobportalsCareer career) {
        if (repository.existsById(id)) {
            career.setId(id);
            return repository.save(career);
        }
        return null;
    }

    @Override
    public void deleteCareer(int id) {
        repository.deleteById(id);
    }
}
