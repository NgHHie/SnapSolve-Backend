package com.example.snapsolve.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/images}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add handler for image uploads directory
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir + "/");
                
        // Keep existing resource handlers if any
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}