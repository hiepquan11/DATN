package com.huynhduc.backend.config;

import com.huynhduc.backend.utils.JWT.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        try {
            if (token != null) {
                username = jwtService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            String refreshToken = null;
            if (request.getCookies() != null) {
                for (var cookie : request.getCookies()) {
                    if ("refresh_token".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (refreshToken != null && jwtService.isRefreshTokenValid(refreshToken)) {
                String refreshUsername = jwtService.extractUsername(refreshToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUsername);

                String newAccessToken = jwtService.generateAccessToken(refreshUsername);

                Cookie newAccessCookie = new Cookie("access_token", newAccessToken);
                newAccessCookie.setHttpOnly(true);
                newAccessCookie.setPath("/");
                newAccessCookie.setMaxAge(60 * 60);
                response.addCookie(newAccessCookie);

                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                if (!response.isCommitted()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Token expired or invalid\"}");
                }
                return;
            }
        }

        if (!response.isCommitted()) {
            filterChain.doFilter(request, response);
        }
    }


}
