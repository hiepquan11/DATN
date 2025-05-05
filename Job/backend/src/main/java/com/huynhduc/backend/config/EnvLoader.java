package com.huynhduc.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//@Component
//public class EnvLoader {
//    @PostConstruct
//    public void loadEnv() {
//        try {
//            // Tải file .env
//            Dotenv dotenv = Dotenv.configure().load();
//
//            // Kiểm tra xem .env có dữ liệu không
//            if (dotenv.entries().isEmpty()) {
//                System.err.println("Warning: .env file is empty or not found.");
//            }
//
//            // Đưa từng giá trị vào hệ thống
//            dotenv.entries().forEach(entry -> {
//                System.setProperty(entry.getKey(), entry.getValue());
//            });
//
//        } catch (Exception e) {
//            System.err.println("Error loading .env file: " + e.getMessage());
//        }
//    }
//}
