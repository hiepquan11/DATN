package com.huynhduc.backend.controller.ExperienceController;

import com.huynhduc.backend.entity.JobportalsExperience;
import com.huynhduc.backend.service.JobportalsExperience.JobportalsExperienceService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
public class JobportalsExperienceController {

    @Autowired
    private JobportalsExperienceService experienceService;

    @GetMapping("/")
    public ResponseEntity<?> getAllExperiences() {
        try {
            List<JobportalsExperience> experiences = experienceService.getAllJobportalsExperiences();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", experiences));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get experiences", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable int id) {
        try {
            JobportalsExperience experience = experienceService.getJobportalsExperienceById(id);
            if (experience == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Experience not found", "No experience with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", experience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get experience", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createExperience(@Validated @RequestBody JobportalsExperience experience) {
        try {
            JobportalsExperience savedExperience = experienceService.createJobportalsExperience(experience);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Experience created successfully", savedExperience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to create experience", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable int id, @Validated @RequestBody JobportalsExperience experience) {
        try {
            JobportalsExperience updatedExperience = experienceService.updateJobportalsExperience(id, experience);
            if (updatedExperience == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Experience not found", "No experience with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Experience updated successfully", updatedExperience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to update experience", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable int id) {
        try {
            experienceService.deleteJobportalsExperience(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Experience deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to delete experience", e.getMessage()));
        }
    }
}
