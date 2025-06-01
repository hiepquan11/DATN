package com.huynhduc.backend.service.JobportalsRating;

import com.huynhduc.backend.DTO.TopCompaniesDTO;

import java.util.List;

public interface JobportalsRatingInterface {
    List<TopCompaniesDTO> getTopCompanies(int limit);
}
