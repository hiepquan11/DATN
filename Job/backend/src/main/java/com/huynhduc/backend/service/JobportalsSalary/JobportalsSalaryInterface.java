package com.huynhduc.backend.service.JobportalsSalary;

import com.huynhduc.backend.entity.JobportalsSalary;

import java.util.List;

public interface JobportalsSalaryInterface {

    List<JobportalsSalary> getAllSalaries();
    JobportalsSalary getSalaryById(int id);
    JobportalsSalary createSalary(JobportalsSalary salary);
    JobportalsSalary updateSalary(int id, JobportalsSalary salary);
    void deleteSalary(int id);
}
