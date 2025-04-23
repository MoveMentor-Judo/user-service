package com.movementor.userservice.authentication;

import com.movementor.userservice.models.User;
import com.movementor.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        // Check if it's OAuth2 authentication
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            return ResponseEntity.ok(oauthToken.getPrincipal().getAttributes());
        }
        // For JWT authentication
        else if (authentication != null) {
            String email = authentication.getName(); // This is the email from JWT

            // Find user by email
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("name", user.getName());
                userInfo.put("email", user.getEmail());
                // Might not have picture stored, so provide a default or null
                userInfo.put("picture", null); // Update this if I want to store user pictures

                return ResponseEntity.ok(userInfo);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/test-save")
    public ResponseEntity<?> testSave() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        return ResponseEntity.ok(userRepository.save(user));
    }

}

