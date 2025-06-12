package com.huynhduc.backend.controller.CvController;

import com.huynhduc.backend.entity.JobportalsCirriculumvitae;
import com.huynhduc.backend.service.CV.CvService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/job-seeker-profiles")
public class CvController {

    @Autowired
    private CvService cvService;

    @GetMapping("/{userId}/curriculum-vitae")
    public ResponseEntity<?> getCvBySeekerProfile(@PathVariable int userId) {
        try {
            List<JobportalsCirriculumvitae> cvs = cvService.findCvSeekerProfile(userId);
            if (cvs.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new SuccessResponse<>(200, "No CV found", null)
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200, "Success", cvs)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Failed to fetch CV", e.getMessage())
            );
        }
    }

    @PostMapping("/{userId}/curriculum-vitae")
    public ResponseEntity<?> uploadCv(
            @PathVariable int userId,
            @RequestParam("url_cv") MultipartFile file
    ) {
        try {
            JobportalsCirriculumvitae cv = cvService.uploadCvFile(userId, file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200, "CV uploaded successfully", cv)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(400, "Failed to upload CV", e.getMessage())
            );
        }
    }

    @DeleteMapping("/job-seeker-profile/cv/{cvId}")
    public ResponseEntity<?> deleteCv(@PathVariable int cvId) {
        try {
            JobportalsCirriculumvitae deleted = cvService.deleteCv(cvId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200, "CV deleted successfully", deleted)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(404, "Failed to delete CV", e.getMessage())
            );
        }
    }
}
