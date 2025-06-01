package com.huynhduc.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TopCompaniesDTO {
    private String company_name;
    private Double avg_rating;
    private String recruiter_avatar;
}
