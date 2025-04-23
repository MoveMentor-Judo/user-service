package com.movementor.userservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtUserResolver {
    public Optional<String> extractUserId(HttpServletRequest request) {
        Object emailObj = request.getAttribute("userEmail");
        if (emailObj instanceof String email) {
            return Optional.of(email);
        }
        return Optional.empty();
    }
}
