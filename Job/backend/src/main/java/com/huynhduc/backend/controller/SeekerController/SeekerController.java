package com.huynhduc.backend.controller.SeekerController;

import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.service.EducationalDetail.EducationDetailService;
import com.huynhduc.backend.service.SeekerProfile.SeekerProfileService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job-seeker-profiles")
public class SeekerController {

    @Autowired
    private SeekerProfileService service;

    @Autowired
    private EducationDetailService eduService;

    @GetMapping("/")
    public Map<String, Object> getJobSeekerProfiles(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "16") int pageSize
    ) {
        PageRequest pageable = PageRequest.of(page - 1, pageSize);

        Page<JobportalsJobseekerprofile> resultPage =
                service.getSeekerProfileWithFilters(filters, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("count", resultPage.getTotalElements());
        response.put("page_size", resultPage.getSize());
        response.put("results", resultPage.getContent());

        return response;
    }

    @GetMapping("{id}/education-detail")
    public ResponseEntity<?> getEducationDetail(@PathVariable int id){

        try {

            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(
                            HttpStatus.OK.value(),
                            "Successfully",
                            eduService.getByUserId(id)
                    )
            );

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "failed to get edu detail",
                            e.getMessage()
                    )
            );
        }
    }
}
