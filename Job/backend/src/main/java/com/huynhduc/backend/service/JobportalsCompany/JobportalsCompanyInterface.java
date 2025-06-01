package com.huynhduc.backend.service.JobportalsCompany;

import com.huynhduc.backend.entity.JobportalsCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobportalsCompanyInterface {

    JobportalsCompany createCompany(JobportalsCompany company);

    Page<JobportalsCompany> getCompaniesWithFilters(Pageable pageable);

    JobportalsCompany getCompanyById(int id);

    JobportalsCompany updateCompany(int id, JobportalsCompany company);

    List<JobportalsCompany> getAllCompanies();

    void deleteCompany(int id);
}
