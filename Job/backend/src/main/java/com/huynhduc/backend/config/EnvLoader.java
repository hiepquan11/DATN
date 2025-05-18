package com.huynhduc.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EnvLoader {

    public static void loadEnvFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Bỏ qua dòng trống và comment
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int idx = line.indexOf('=');
                if (idx == -1) {
                    continue; // dòng không hợp lệ
                }

                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();

                // Loại bỏ dấu nháy nếu có (như "value" hoặc 'value')
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                        (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }

                // Đặt biến môi trường cho JVM
                System.setProperty(key, value);
            }
        } catch (IOException e) {
            System.err.println("Failed to load .env file: " + e.getMessage());
        }
    }
}
