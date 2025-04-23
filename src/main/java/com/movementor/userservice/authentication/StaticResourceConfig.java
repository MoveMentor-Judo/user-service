package com.movementor.userservice.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /videos/ to the physical location where videos are stored
        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:uploads/"); // Adjust this path to your video directory
    }
}
