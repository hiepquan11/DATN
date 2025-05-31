package com.huynhduc.backend.controller.authController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huynhduc.backend.DTO.AuthDTO;
import com.huynhduc.backend.DTO.LoginDTO;
import com.huynhduc.backend.DTO.RegisterDTO;
import com.huynhduc.backend.utils.Cookie.CookieUtils;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class authController {

    @Autowired
    private JobportalsUserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterDTO registerDTO) {
        try {
            JobportalsUser newUser = new JobportalsUser();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(registerDTO.getPassword());
            newUser.setDateJoined(new Date());

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
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try {
            AuthDTO authDTO = userService.login(loginDTO.getUsername(), loginDTO.getPassword());

            CookieUtils.addCookie(response, "accessToken", authDTO.getAccessToken(), 60 * 15, true, true, "/");
            CookieUtils.addCookie(response, "refreshToken", authDTO.getRefreshToken(), 60 * 60 * 24 * 7, true, true, "/");

            String userJson = new ObjectMapper().writeValueAsString(authDTO.getUser());
            String encodedUserJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8.toString());

            CookieUtils.addCookie(response, "current_user", encodedUserJson, 60 * 60 * 24 * 7, false, true, "/");

            return ResponseEntity.ok(
                    new SuccessResponse<>(200, "Đăng nhập thành công", authDTO)
            );

        } catch (RuntimeException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorResponse(401, "Đăng nhập thất bại", e.getMessage())
            );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
