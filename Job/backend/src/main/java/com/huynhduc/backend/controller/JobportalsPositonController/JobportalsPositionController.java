package com.huynhduc.backend.controller.JobportalsPositonController;

import com.huynhduc.backend.entity.JobportalsPosition;
import com.huynhduc.backend.service.JobportalsPostion.JobportalsPositionService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class JobportalsPositionController {

    @Autowired
    private JobportalsPositionService positionService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPositions() {
        try {
            List<JobportalsPosition> positions = positionService.getAllJobportalsPositions();
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", positions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to get positions", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable int id) {
        try {
            JobportalsPosition position = positionService.getJobportalsPositionById(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", position));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Position not found", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createPosition(@Validated @RequestBody JobportalsPosition position) {
        try {
            JobportalsPosition createdPosition = positionService.createJobportalsPosition(position);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Position created successfully", createdPosition));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to create position", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable int id, @Validated @RequestBody JobportalsPosition position) {
        try {
            JobportalsPosition updatedPosition = positionService.updateJobportalsPosition(id, position);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Position updated successfully", updatedPosition));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Failed to update position", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable int id) {
        try {
            positionService.deleteJobportalsPosition(id);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Position deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to delete position", e.getMessage()));
        }
    }
}
