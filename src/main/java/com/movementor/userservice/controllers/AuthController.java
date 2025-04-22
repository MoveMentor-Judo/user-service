package com.movementor.userservice.controllers;

import com.movementor.userservice.components.JwtUtil;
import com.movementor.userservice.models.User;
import com.movementor.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test-login")
    public ResponseEntity<?> testJwtIssue() {
        String email = "test@example.com"; // mock email
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
    @GetMapping("/me")
    public ResponseEntity<?> getTokenFromContext(OAuth2AuthenticationToken token) {
        return ResponseEntity.ok(token.getPrincipal().getAttributes());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(OAuth2AuthenticationToken authentication) {
        String email = authentication.getPrincipal().getAttribute("email");

        // Ensure user is stored in MongoDB
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(authentication.getPrincipal().getAttribute("name"));
            return userRepository.save(newUser);
        });

        // Create token
        String token = jwtUtil.generateToken(email);

        // Return token to frontend
        return ResponseEntity.ok(Map.of("token", token));
    }
}

