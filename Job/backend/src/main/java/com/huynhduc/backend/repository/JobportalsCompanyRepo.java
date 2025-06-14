package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "company")
public interface JobportalsCompanyRepo extends JpaRepository<JobportalsCompany, Integer>, JpaSpecificationExecutor<JobportalsCompany> {


    JobportalsCompany findByRecruiterId(int id);
}
