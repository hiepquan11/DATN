package com.huynhduc.backend.controller.SalaryController;

import com.huynhduc.backend.entity.JobportalsSalary;
import com.huynhduc.backend.service.JobportalsSalary.JobportalsSalaryService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class JobportalsSalaryController {

    @Autowired
    private JobportalsSalaryService salaryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSalaries() {
        try {
            List<JobportalsSalary> salaries = salaryService.getAllSalaries();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", salaries));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get salaries", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable int id) {
        try {
            JobportalsSalary salary = salaryService.getSalaryById(id);
            if (salary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Salary not found", "No salary with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", salary));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get salary", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createSalary(@Validated @RequestBody JobportalsSalary salary) {
        try {
            JobportalsSalary savedSalary = salaryService.createSalary(salary);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Salary created successfully", savedSalary));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to create salary", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalary(@PathVariable int id, @Validated @RequestBody JobportalsSalary salary) {
        try {
            JobportalsSalary updatedSalary = salaryService.updateSalary(id, salary);
            if (updatedSalary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Salary not found", "No salary with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Salary updated successfully", updatedSalary));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to update salary", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable int id) {
        try {
            salaryService.deleteSalary(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Salary deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to delete salary", e.getMessage()));
        }
    }
}
