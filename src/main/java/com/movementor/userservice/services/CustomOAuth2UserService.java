package com.movementor.userservice.services;

import com.movementor.userservice.models.User;
import com.movementor.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("🔄 CustomOAuth2UserService TRIGGERED");
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        System.out.println("🔄 CustomOAuth2UserService TRIGGERED for: " + email);

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            System.out.println("🆕 Saving new user: " + email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            return userRepository.save(newUser);
        });

        // Required: Return a full OAuth2User with authorities and attributes
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),             // Pass authorities
                oAuth2User.getAttributes(),              // Pass all attributes (e.g., email, name)
                "email"                                  // Use "email" as the key for name attribute
        );
    }

}

