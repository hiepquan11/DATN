package com.huynhduc.backend.repository;

import com.huynhduc.backend.entity.JobportalsPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payment")
public interface PaymentRepo extends JpaRepository<JobportalsPayment, Integer> {
}
