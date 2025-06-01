package com.huynhduc.backend.controller.JobportalsController;

import com.huynhduc.backend.DTO.TopCompaniesDTO;
import com.huynhduc.backend.service.JobportalsRating.JobportalsRatingService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/top-company")
public class JobportalsRatingController {

    @Autowired
    private JobportalsRatingService ratingService;

    @GetMapping("")
    public ResponseEntity<?> getTopCompanies(@RequestParam(defaultValue = "20") int limit) {
        try {
            List<TopCompaniesDTO> topCompanies = ratingService.getTopCompanies(limit);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", topCompanies));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to fetch top companies", e.getMessage()));
        }
    }
}
