package com.moloom.img.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: moloom
 * @date: 2024-12-26 16:31
 * @description: handle CORS
 */
@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // 允许 /api 路径进行跨域
                .allowedOrigins("http://localhost:5173",
                        "https://img.moloom.cn",
                        "https://img.moloom.com")        // 允许的请求来源
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的请求方式
                .allowedHeaders("*")         // 允许的请求头
                .allowCredentials(true);        // 是否允许携带凭证（如 cookies）
    }
}
