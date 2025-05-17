package com.huynhduc.backend.controller.authController;

import com.huynhduc.backend.DTO.LoginDTO;
import com.huynhduc.backend.DTO.RegisterDTO;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class authController {

    @Autowired
    private JobportalsUserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterDTO registerDTO){
        try {
            JobportalsUser newUser = new JobportalsUser();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(registerDTO.getPassword());
            newUser.setDateJoined(LocalDateTime.now());

            JobportalsUser savedUser = userService.register(newUser, registerDTO.isRecruiter());

            return ResponseEntity.ok().body(new SuccessResponse<>(
                    201, "Successfully", savedUser
            ));

        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Failed to create user",
                    e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO){
        try {

            return ResponseEntity.ok().body(new SuccessResponse<>(
                    200,
                    "Đăng nhập thành công",
                    userService.login(loginDTO.getUsername(), loginDTO.getPassword())
            ));
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Đăng nhập không thành công",
                    e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
