package com.example.security.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationControllerInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationControllerInterceptor authenticationControllerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationControllerInterceptor).addPathPatterns("/api/v1/auth/**"); // Specify the URL pattern to apply the interceptor
    }
}
