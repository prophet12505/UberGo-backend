package com.example.ubergo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:*/", "https://localhost:*/","http://localhost:5174/","http://localhost:5173/")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}