package com.huynhduc.backend.controller.JobportalsCityController;

import com.huynhduc.backend.entity.JobportalsCity;
import com.huynhduc.backend.service.JobportalsCity.JobportalsCityService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class JobportalsCityController {

    @Autowired
    private JobportalsCityService cityService;

    @GetMapping("")
    public ResponseEntity<?> getAllCities() {
        try {
            List<JobportalsCity> cities = cityService.getAllCities();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy danh sách thành phố thành công", cities));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable int id) {
        try {
            JobportalsCity city = cityService.getCityById(id);
            if (city == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Không tìm thấy thành phố với id: " + id, ""));
            }
            return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy thành phố thành công", city));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createCity(@RequestBody JobportalsCity city) {
        try {
            JobportalsCity savedCity = cityService.saveCity(city);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Tạo thành phố thành công", savedCity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Tạo thành phố thất bại", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        try {
            cityService.deleteCity(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Xóa thành phố thành công", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Không tìm thấy thành phố để xóa", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi server", e.getMessage()));
        }
    }
}
