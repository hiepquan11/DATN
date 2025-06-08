package com.huynhduc.backend.controller.JobportalsCompanyController;

import com.huynhduc.backend.entity.JobportalsCompany;
import com.huynhduc.backend.service.JobportalsCompany.JobportalsCompanyService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class JobportalsCompanyController {

    @Autowired
    private JobportalsCompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<?> getCompaniesWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int page_size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, page_size);
            Page<JobportalsCompany> result = companyService.getCompaniesWithFilters(pageable);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("content", result.getContent());
            responseData.put("page", result.getNumber());
            responseData.put("page_size", result.getSize());
            responseData.put("total_pages", result.getTotalPages());
            responseData.put("count", result.getTotalElements());

            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy danh sách công ty thành công", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Không thể lấy danh sách công ty", e.getMessage()));
        }
    }

    // Lấy company theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable int id) {
        try {
            JobportalsCompany company = companyService.getCompanyById(id);
            if (company == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Không tìm thấy công ty", "Công ty với id " + id + " không tồn tại"));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy công ty thành công", company));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi khi lấy công ty", e.getMessage()));
        }
    }

    // Tạo company mới
    @PostMapping("/")
    public ResponseEntity<?> createCompany(@Validated @RequestBody JobportalsCompany company) {
        try {
            JobportalsCompany savedCompany = companyService.createCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Tạo công ty thành công", savedCompany));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Không thể tạo công ty", e.getMessage()));
        }
    }

    // Cập nhật company
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable int id, @Validated @RequestBody JobportalsCompany company) {
        try {
            JobportalsCompany updatedCompany = companyService.updateCompany(id, company);
            if (updatedCompany == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Không tìm thấy công ty để cập nhật", "ID không tồn tại"));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật công ty thành công", updatedCompany));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Không thể cập nhật công ty", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable int id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Xóa công ty thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Không thể xóa công ty", e.getMessage()));
        }
    }


}
