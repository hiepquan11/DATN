package com.huynhduc.backend.controller.RecruiterController;

import com.huynhduc.backend.entity.JobportalsCompany;
import com.huynhduc.backend.entity.JobportalsJobpost;

import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.service.JobportalsCompany.JobportalsCompanyService;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobpostService;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import com.huynhduc.backend.utils.JWT.JWTService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class RecruiterController {

    @Autowired
    private JobportalsJobpostService jobPostService;

    @Autowired
    private JobportalsUserService userService;

    @Autowired
    private JobportalsCompanyService companyService;

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = "/{userId}/job-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createJobPost(
            @PathVariable("userId") int userId,
            @ModelAttribute JobportalsJobpost jobPost,
            HttpServletRequest request) {

        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            if (token == null || !jwtService.isTokenValid(token, jwtService.extractUsername(token))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse(401, "Unauthorized: Token is missing or invalid", null));
            }

            String usernameFromToken = jwtService.extractUsername(token);

            JobportalsUser authenticatedUser = userService.getUserByUsername(usernameFromToken);

            if (authenticatedUser == null || authenticatedUser.getId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse(403, "Forbidden: User ID không khớp với token", null));
            }

            jobPost.setRecruiter(authenticatedUser);
            JobportalsJobpost created = jobPostService.createJobPost(jobPost);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new SuccessResponse<>(201, "Tạo bài đăng thành công", created)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(400, "Không thể tạo bài đăng", e.getMessage())
            );
        }
    }

    @PatchMapping(value = "/job-posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateJobPost(
            @PathVariable int id,
            @ModelAttribute JobportalsJobpost jobPost) {

        try {
            JobportalsJobpost updated = jobPostService.updateJobPost(id, jobPost);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật thành công", updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(400, "Không thể cập nhật", e.getMessage())
            );
        }
    }

    @GetMapping("/{id}/job-post")
    public ResponseEntity<?> getJobPostDetail (@PathVariable int id) {
        List<JobportalsJobpost> listJob = jobPostService.getJobPostsByRecruiterId(id);
        if (listJob == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not found",null)
            );
        }
        return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy dữ liệu thành công", listJob));
    }

    @GetMapping("/{id}/company")
    public ResponseEntity<?> getByRecruiterId(@PathVariable int id) {
        try {
            JobportalsCompany company = companyService.getByRecruiterId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200,"Success", company)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500,"Cannot get company by recruiter id", e.getMessage()));
        }
    }
}
