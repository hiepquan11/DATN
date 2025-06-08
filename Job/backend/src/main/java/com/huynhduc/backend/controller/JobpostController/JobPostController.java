package com.huynhduc.backend.controller.JobpostController;

import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobPostInterface;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobpostService;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job-posts")
public class JobPostController {

    @Autowired
    private JobportalsJobpostService jobPostService;

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

    @GetMapping("/{id}/job-posts/")
    public ResponseEntity<?> getJobPostDetail (@PathVariable int id) {
        JobportalsJobpost jobPost = jobPostService.getJobPostById(id);
        if (jobPost == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not found",null)
            );
        }
        return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy dữ liệu thành công", jobPost));
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

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateJobPost(@PathVariable int id, @RequestBody JobportalsJobpost jobPost) {
//        try {
//            JobportalsJobpost updated = jobPostService.updateJobPost(id, jobPost);
//            if (updated == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                        new ErrorResponse(404, "Không tìm thấy bài đăng để cập nhật", "ID không tồn tại")
//                );
//            }
//            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật bài đăng thành công", updated));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ErrorResponse(400, "Không thể cập nhật bài đăng", e.getMessage())
//            );
//        }
//    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
            @RequestParam(required = false) String kw,
            @RequestParam(required = false) String city_id,
            @RequestParam(required = false) String working_form_id,
            @RequestParam(required = false) String position_id,
            @RequestParam(required = false) String experience_id,
            @RequestParam(required = false) String salary_id,
            @RequestParam(required = false) String career_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int page_size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, page_size);
            Map<String, String> filters = new HashMap<>();

            if (kw != null && !kw.isBlank()) {
                filters.put("keyword", kw);
            }

            if (city_id != null && !city_id.isBlank()) {
                filters.put("city", city_id);
            }

            if (working_form_id != null && !working_form_id.isBlank()) {
                filters.put("working_form", working_form_id);
            }

            if (position_id != null && !position_id.isBlank()) {
                filters.put("position", position_id);
            }

            if (experience_id != null && !experience_id.isBlank()) {
                filters.put("experience", experience_id);
            }

            if (salary_id != null && !salary_id.isBlank()) {
                filters.put("salary", salary_id);
            }

            if (career_id != null && !career_id.isBlank()) {
                filters.put("career", career_id);
            }

            Page<JobportalsJobpost> result = jobPostService.getJobPostsWithFilters(filters, pageable);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("content", result.getContent());
            responseData.put("page", result.getNumber());
            responseData.put("page_size", result.getSize());
            responseData.put("total_pages", result.getTotalPages());
            responseData.put("count", result.getTotalElements());

            return ResponseEntity.ok(new SuccessResponse<>(200, "Tìm kiếm bài đăng thành công", responseData));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Không thể tìm kiếm bài đăng", e.getMessage()));
        }
    }

    public ResponseEntity<?> getSeekerByJobPostId(@PathVariable int id) {
        return null;
    }
}
