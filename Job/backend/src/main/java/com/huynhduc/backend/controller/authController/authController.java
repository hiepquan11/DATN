package com.huynhduc.backend.controller.authController;

import com.huynhduc.backend.DTO.RegisterDTO;
import com.huynhduc.backend.Response.ErrorResponse;
import com.huynhduc.backend.Response.SuccessResponse;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class authController {

    @Autowired
    private JobportalsUserService userService;
    @Autowired
    private JobportalsUserRepo repo;

    @PostMapping("/")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterDTO registerDTO){
        if(userService.findByUsername(registerDTO.getUsername()) != null){
            return ResponseEntity.badRequest().body(new ErrorResponse(
                    500, "Failed to create user", "username is existed"
            ));
        }
        JobportalsUser newUser = new JobportalsUser();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(registerDTO.getPassword());
        newUser.setDateJoined(LocalDateTime.now());
        repo.save(newUser);
        return ResponseEntity.ok().body(new SuccessResponse<>(
                200, "Successfully",newUser
        ));
    }
}
