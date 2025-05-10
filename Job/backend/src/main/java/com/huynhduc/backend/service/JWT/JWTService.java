package com.huynhduc.backend.service.JWT;

import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {

    // Secret key for signing the JWT (must be base64-encoded and long enough for HS256)
    private static final String SECRET_KEY = "MTIzNDU2NDU5OThEMzIxM0F6eGMzNTE2NTQzMjEzMjE2NTQ5OHEzMTNhMnMxZDMyMnp4M2MyMQ==";

    @Autowired
    private JobportalsUserService userService;

    // Generate a signing key from the secret
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate JWT token based on username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        // Get user info to add custom claims (like id and role)
        JobportalsUser user = userService.findByUsername(username);
        if (user != null && user.getRoles() != null) {
            claims.put("id", user.getId());
            claims.put("role", user.getRoles().stream().findFirst().get().getRole_name());
        }

        // Build and return the JWT token
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(username) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Issue time
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // Expiration time (1 hour)
                .signWith(SignatureAlgorithm.HS256, getSignInKey()) // Sign with HS256 and secret key
                .compact(); // Generate the compact JWT string
    }

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Validate if the token is valid for a given username
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a specific claim using a resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse and extract all claims from the token
    // Parse and extract all claims from the token using the JwtParser
    private Claims extractAllClaims(String token) {
        // Build the JwtParser
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSignInKey()) // Set the secret key for signature validation
                .build();

        // Parse the token and get the claims
        return parser.parseClaimsJws(token).getBody(); // This will return the claims body
    }

}
