package com.freelance.bookCar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng cho tất cả các đường dẫn
                .allowedOrigins("*")  // Chấp nhận tất cả các cổng trên localhost
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Các phương thức HTTP được chấp nhận
                .allowedHeaders("*")  // Chấp nhận tất cả các loại header
                .allowCredentials(true)  // Cho phép credentials như cookie
                .maxAge(3600);  // Cache CORS response trong 1 giờ
    }
}