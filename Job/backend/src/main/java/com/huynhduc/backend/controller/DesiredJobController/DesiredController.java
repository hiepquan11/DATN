package com.huynhduc.backend.controller.DesiredJobController;

import com.huynhduc.backend.service.DesiredJob.DesiredJobService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-seeker-profiles")
public class DesiredController {

    @Autowired
    private DesiredJobService desiredJobService;

    @GetMapping("/{id}/desired-job")
    public ResponseEntity<?> getDesiredJobBySeeker(@PathVariable  int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(
                            HttpStatus.OK.value(),
                            "Successfully",
                            desiredJobService.findBySeeker(id)
                    )
            );
        } catch (Exception e){
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to get desired",e.getMessage())
            );
        }
    }
}
