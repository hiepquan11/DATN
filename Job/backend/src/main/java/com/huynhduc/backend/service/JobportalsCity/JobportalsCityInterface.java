package com.huynhduc.backend.service.JobportalsCity;

import com.huynhduc.backend.entity.JobportalsCity;

import java.util.List;

public interface JobportalsCityInterface {
    List<JobportalsCity> getAllCities();
    JobportalsCity getCityById(int id);
    JobportalsCity saveCity(JobportalsCity city);
    void deleteCity(int id);
}
