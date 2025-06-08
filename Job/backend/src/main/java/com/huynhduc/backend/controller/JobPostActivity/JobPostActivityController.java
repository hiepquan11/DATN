package com.huynhduc.backend.controller.JobPostActivity;

import com.huynhduc.backend.entity.JobportalsJobpostactivity;
import com.huynhduc.backend.service.JobportalsJobPostActivity.JobportalsJobPostActivityService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job-posts")
public class JobPostActivityController {

    @Autowired
    private JobportalsJobPostActivityService service;

    @GetMapping("/{id}/applied-job-post")
    public ResponseEntity<?> getJobActivityByJobId(@PathVariable int id) {
        try {
            List<JobportalsJobpostactivity> listActivity = service.getJJobPostActivityByJobId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(HttpStatus.OK.value(),"Success", listActivity )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal Server Error", e.getMessage() )
            );
        }
    }
}
