package com.huynhduc.backend.controller.SeekerController;

import com.huynhduc.backend.entity.JobportalsJobpostactivity;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobpostService;
import com.huynhduc.backend.service.JobportalsJobPostActivity.JobportalsJobPostActivityService;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import com.huynhduc.backend.service.SeekerProfile.SeekerProfileService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class SeekerProfile {

    @Autowired
    private SeekerProfileService service;

    @Autowired
    private JobportalsJobPostActivityService jobPostActivityService;

    @Autowired
    private JobportalsUserService userService;

    @Autowired
    private JobportalsJobpostService jobService;

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

    @GetMapping("/{userId}/job-posts-activity")
    public ResponseEntity<?> getJobActivityBySeeker(@PathVariable int userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(
                            HttpStatus.OK.value(),
                            "Successfully",
                            jobPostActivityService.getBySeekerId(userId)
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500,"failed to get job apply", e.getMessage())
            );
        }
    }

    @PostMapping("/{userId}/job-post-activity")
    public ResponseEntity<?> applyJobPost(
            @PathVariable int userId,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer jobPostId = requestBody.get("job_post_id");
            if (jobPostId == null) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponse(400, "Missing job_post_id", null)
                );
            }

            var user = userService.getById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "User not found", null)
                );
            }

            var jobPost = jobService.getJobPostById(jobPostId);
            if (jobPost == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Job post not found", null)
                );
            }

            if (jobPostActivityService.hasApplied(userId, jobPostId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ErrorResponse(400, "You have already applied for this job", null)
                );
            }

            var activity = new JobportalsJobpostactivity();
            activity.setJobPost(jobPost);
            activity.setSeeker(user);
            activity.setApply_date(new Date());
            activity.setStatus(true);

            var saved = jobPostActivityService.createJobPostActivity(activity);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new SuccessResponse<>(201, "Apply successfully", Map.of("id", saved.getId()))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Apply job post failed", e.getMessage())
            );
        }
    }
}
