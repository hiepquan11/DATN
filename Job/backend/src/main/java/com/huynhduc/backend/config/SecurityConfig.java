package com.huynhduc.backend.config;

import com.huynhduc.backend.endpoint.Endpoint;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(JobportalsUserService userService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));  // FE domain
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(Endpoint.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(Endpoint.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(Endpoint.AUTHENTICATED_COMMON_ENDPOINTS).authenticated()

                        .requestMatchers(Endpoint.ADMIN_GET_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(Endpoint.ADMIN_POST_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(Endpoint.ADMIN_DELETE_ENDPOINTS).hasRole("ADMIN")

                        .requestMatchers(Endpoint.RECRUITER_GET_ENDPOINTS).hasRole("RECRUITER")
                        .requestMatchers(Endpoint.RECRUITER_POST_ENDPOINTS).hasRole("RECRUITER")
                        .requestMatchers(Endpoint.RECRUITER_PUT_ENDPOINTS).hasRole("RECRUITER")
                        .requestMatchers(Endpoint.RECRUITER_DELETE_ENDPOINTS).hasRole("RECRUITER")

                        .requestMatchers(Endpoint.SEEKER_GET_ENDPOINTS).hasRole("SEEKER")
                        .requestMatchers(Endpoint.SEEKER_POST_ENDPOINTS).hasRole("SEEKER")
                        .requestMatchers(Endpoint.SEEKER_PUT_ENDPOINTS).hasRole("SEEKER")
                        .requestMatchers(Endpoint.SEEKER_DELETE_ENDPOINTS).hasRole("SEEKER")
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
