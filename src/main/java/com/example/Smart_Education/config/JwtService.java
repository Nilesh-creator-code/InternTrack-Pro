package com.example.Smart_Education.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static final String SECRET_KEY = "nileshvishwakarmajavasoftwaredeveloper"; // Replace with your actual secret key
    
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email) {

        return "generated-jwt-token"; // Replace with actual token generation logic
    }

}

