package com.huynhduc.backend.service.JobportalsCompany;

import com.huynhduc.backend.entity.JobportalsCompany;

import java.util.List;

public interface JobportalsCompanyInterface {
    JobportalsCompany createCompany(JobportalsCompany company);

    List<JobportalsCompany> getAllCompanies();

    JobportalsCompany getCompanyById(int id);

    JobportalsCompany updateCompany(int id, JobportalsCompany company);

    void deleteCompany(int id);
}
