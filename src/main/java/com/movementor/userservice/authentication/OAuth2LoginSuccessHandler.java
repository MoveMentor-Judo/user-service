package com.movementor.userservice.authentication;

import com.movementor.userservice.models.User;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.movementor.userservice.repositories.UserRepository;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException, java.io.IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttribute("email");
        String name = oauthToken.getPrincipal().getAttribute("name");

        String jwt = jwtUtil.generateToken(email);
        System.out.println("SuccessHandler: email = " + email);
        // Save user logic - this will run on every successful OAuth login
        if (email != null) {
            userRepository.findByEmail(email).orElseGet(() -> {
                System.out.println("Creating new user: " + email);
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                return userRepository.save(newUser);
            });
        }
        // Redirect to frontend with token as query param
        response.sendRedirect("http://localhost:3000/signin?token=" + jwt);
    }
}

