package com.huynhduc.backend.controller.SeekerController;

import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.service.SeekerProfile.SeekerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job-seeker-profiles")
public class SeekerController {

    @Autowired
    private SeekerProfileService service;

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
}
