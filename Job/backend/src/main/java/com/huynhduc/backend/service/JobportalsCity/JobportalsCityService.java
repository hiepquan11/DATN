package com.huynhduc.backend.service.JobportalsCity;

import com.huynhduc.backend.entity.JobportalsCity;
import com.huynhduc.backend.repository.JobportalsCityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobportalsCityService implements JobportalsCityInterface {

    @Autowired
    private JobportalsCityRepo cityRepository;

    @Override
    public List<JobportalsCity> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public JobportalsCity getCityById(int id) {
        Optional<JobportalsCity> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    @Override
    public JobportalsCity saveCity(JobportalsCity city) {
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(int id) {
        cityRepository.deleteById(id);
    }
}
