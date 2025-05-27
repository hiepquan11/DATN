package com.huynhduc.backend.controller.JobportalsWorkingFormController;

import com.huynhduc.backend.entity.JobportalsWorkingform;
import com.huynhduc.backend.service.WorkingForm.JobportalsWorkingFormService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-forms")
public class JobportalsWorkingFormController {

    @Autowired
    private JobportalsWorkingFormService workingFormService;

    @GetMapping("/")
    public ResponseEntity<?> getAllWorkingForms() {
        try {
            List<JobportalsWorkingform> list = workingFormService.getAllWorkingForms();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy danh sách hình thức làm việc thành công", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkingFormById(@PathVariable int id) {
        try {
            JobportalsWorkingform workingForm = workingFormService.getWorkingFormById(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy hình thức làm việc thành công", workingForm));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Không tìm thấy hình thức làm việc", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createWorkingForm(@RequestBody JobportalsWorkingform workingForm) {
        try {
            JobportalsWorkingform created = workingFormService.createWorkingForm(workingForm);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Tạo hình thức làm việc thành công", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Tạo thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkingForm(@PathVariable int id, @RequestBody JobportalsWorkingform workingForm) {
        try {
            JobportalsWorkingform updated = workingFormService.updateWorkingForm(id, workingForm);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Không tìm thấy hình thức làm việc", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Cập nhật thất bại", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkingForm(@PathVariable int id) {
        try {
            workingFormService.deleteWorkingForm(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Xóa hình thức làm việc thành công", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Không tìm thấy hình thức làm việc để xóa", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }
}
