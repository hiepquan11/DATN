package com.huynhduc.backend.controller.JobportalsViewJobPost;

import com.huynhduc.backend.service.JobportalsViewJobPost.JobportalsViewJobPostService;
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
@RequestMapping("/job-posts")
public class JobportalsViewJobPostController {

    @Autowired
    private JobportalsViewJobPostService viewService;

    @GetMapping("/{id}/view")
    public ResponseEntity<?> getJobPostViewCount(@PathVariable int id) {
        try {
            int viewCount = viewService.GetViewByJobPostId(id);

            if (viewCount == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Not found", "No view record found for job post id " + id));
            }

            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", viewCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get view count", e.getMessage()));
        }
    }

}
