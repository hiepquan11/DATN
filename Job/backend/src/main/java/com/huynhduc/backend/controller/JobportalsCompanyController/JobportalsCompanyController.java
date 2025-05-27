package com.huynhduc.backend.controller.JobportalsCompanyController;

import com.huynhduc.backend.entity.JobportalsCompany;
import com.huynhduc.backend.service.JobportalsCompany.JobportalsCompanyService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class JobportalsCompanyController {

    @Autowired
    private JobportalsCompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCompanies() {
        try {
            List<JobportalsCompany> companies = companyService.getAllCompanies();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", companies));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get companies", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable int id) {
        try {
            JobportalsCompany company = companyService.getCompanyById(id);
            if (company == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Company not found", "No company with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", company));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get company", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createCompany(@Validated @RequestBody JobportalsCompany company) {
        try {
            JobportalsCompany savedCompany = companyService.createCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Company created successfully", savedCompany));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to create company", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable int id, @Validated @RequestBody JobportalsCompany company) {
        try {
            JobportalsCompany updatedCompany = companyService.updateCompany(id, company);
            if (updatedCompany == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Company not found", "No company with id " + id));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Company updated successfully", updatedCompany));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to update company", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable int id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Company deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to delete company", e.getMessage()));
        }
    }
}
