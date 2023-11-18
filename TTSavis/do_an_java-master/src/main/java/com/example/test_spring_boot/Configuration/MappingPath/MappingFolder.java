package com.example.test_spring_boot.Configuration.MappingPath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MappingFolder implements WebMvcConfigurer {
    @Value("${config.upload_folder}")
    String UPLOAD_FILE;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:"+ UPLOAD_FILE);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}