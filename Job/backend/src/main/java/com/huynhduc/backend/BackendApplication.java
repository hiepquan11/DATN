package com.huynhduc.backend;

import com.huynhduc.backend.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		EnvLoader.loadEnvFile(".env");
		SpringApplication.run(BackendApplication.class, args);
	}

}
