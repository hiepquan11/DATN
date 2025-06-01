package com.huynhduc.backend.service.JobportalsRating;

import com.huynhduc.backend.DTO.TopCompaniesDTO;
import com.huynhduc.backend.repository.JobportalsRatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsRatingService implements JobportalsRatingInterface{

    @Autowired
    private JobportalsRatingRepo repo;

    @Override
    public List<TopCompaniesDTO> getTopCompanies(int limit) {
        return repo.findTopCompanies(PageRequest.of(0, limit));
    }
}
