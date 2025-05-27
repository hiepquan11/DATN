package com.huynhduc.backend.controller.JobportalsCareer;

import com.huynhduc.backend.entity.JobportalsCareer;
import com.huynhduc.backend.service.JobportalsCareer.JobportalsCareerService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careers")
public class JobportalsCareerController {

    @Autowired
    private JobportalsCareerService careerService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCareers() {
        try {
            List<JobportalsCareer> careers = careerService.getAllCareers();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", careers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get careers", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCareerById(@PathVariable int id) {
        try {
            JobportalsCareer career = careerService.getCareerById(id);
            if (career == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Career not found", "No career with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", career));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get career", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createCareer(@Validated @RequestBody JobportalsCareer career) {
        try {
            JobportalsCareer savedCareer = careerService.saveCareer(career);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Career created successfully", savedCareer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to create career", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCareer(@PathVariable int id, @Validated @RequestBody JobportalsCareer career) {
        try {
            JobportalsCareer updatedCareer = careerService.updateCareer(id, career);
            if (updatedCareer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Career not found", "No career with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Career updated successfully", updatedCareer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to update career", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable int id) {
        try {
            careerService.deleteCareer(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Career deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to delete career", e.getMessage()));
        }
    }
}
