package com.huynhduc.backend.controller.SeekerController;

import com.huynhduc.backend.service.SeekerProfile.SeekerProfileService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SeekerProfile {

    @Autowired
    private SeekerProfileService service;

    @GetMapping("/{userId}/job-seeker-profile")
    public ResponseEntity<?> getProfileById(@PathVariable int userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200,"Success", service.getJobSeekerProfileById(userId))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500,"failed to get job seeker profile", e.getMessage())
            );
        }
    }
}
