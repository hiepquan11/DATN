package com.huynhduc.backend.repository;

import com.huynhduc.backend.DTO.TopCompaniesDTO;
import com.huynhduc.backend.entity.JobportalsRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "rating")
public interface JobportalsRatingRepo extends JpaRepository<JobportalsRating, Integer> {
    @Query("""
        SELECT new com.huynhduc.backend.DTO.TopCompaniesDTO(
            r.company.company_name,
            AVG(r.rating),
            r.company.recruiter.avatar
        )
        FROM JobportalsRating r
        GROUP BY r.company.id, r.company.company_name, r.company.recruiter.avatar
        ORDER BY AVG(r.rating) DESC
    """)
    List<TopCompaniesDTO> findTopCompanies(Pageable pageable);

}
