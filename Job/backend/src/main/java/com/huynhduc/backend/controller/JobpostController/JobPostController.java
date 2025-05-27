package com.huynhduc.backend.controller;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobPostInterface;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job-posts")
public class JobPostController {

    @Autowired
    private JobportalsJobPostInterface jobPostService;

    @GetMapping
    public ResponseEntity<?> getAllJobPosts() {
        try {
            List<JobportalsJobpost> posts = jobPostService.getAllJobPosts();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy danh sách bài đăng thành công", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Không thể lấy danh sách bài đăng", e.getMessage())
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobPostById(@PathVariable int id) {
        try {
            JobportalsJobpost post = jobPostService.getJobPostById(id);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Không tìm thấy bài đăng", "Bài đăng với ID " + id + " không tồn tại")
                );
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy bài đăng thành công", post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Lỗi khi lấy bài đăng", e.getMessage())
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> createJobPost(@RequestBody JobportalsJobpost jobPost) {
        try {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobPost(@PathVariable int id, @RequestBody JobportalsJobpost jobPost) {
        try {
            JobportalsJobpost updated = jobPostService.updateJobPost(id, jobPost);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Không tìm thấy bài đăng để cập nhật", "ID không tồn tại")
                );
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật bài đăng thành công", updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(400, "Không thể cập nhật bài đăng", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobPost(@PathVariable int id) {
        try {
            JobportalsJobpost post = jobPostService.getJobPostById(id);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Không tìm thấy bài đăng để xóa", "ID không tồn tại")
                );
            }
            jobPostService.deleteJobPost(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Xóa bài đăng thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Không thể xóa bài đăng", e.getMessage())
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getJobPostsWithFilters(
            @RequestParam(required = false) String job_name,
            @RequestParam(required = false) String city_name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int page_size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, page_size);

            Map<String, String> filters = new HashMap<>();
            if (job_name != null && !job_name.isEmpty()) {
                filters.put("job_name", job_name);
            }
            if (city_name != null && !city_name.isEmpty()) {
                filters.put("city_name", city_name);
            }

            Page<JobportalsJobpost> result = jobPostService.getJobPostsWithFilters(filters, pageable);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("content", result.getContent());
            responseData.put("count", result.getTotalElements());
            responseData.put("page_size", result.getSize());

            return ResponseEntity.ok(new SuccessResponse<>(200, "Tìm kiếm bài đăng thành công", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, "Không thể tìm kiếm bài đăng", e.getMessage())
            );
        }
    }
}
