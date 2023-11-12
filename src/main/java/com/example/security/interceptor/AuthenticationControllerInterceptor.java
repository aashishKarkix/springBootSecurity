package com.example.security.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthenticationControllerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationControllerInterceptor.class);

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        logger.info("Intercepting request to AuthenticationController: {}", request.getRequestURI());

        if (request.getRequestURI().endsWith("/api/v1/auth/register")) {
            return true;
        }

        //Check if the request has certain headers
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            logger.warn("Request does not have Authorization header");
            // You can modify the response or throw an exception if needed
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  // Stop further processing
        }

        // Continue with the request processing
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // This method is called after the handler is executed
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("Request to AuthenticationController completed: {}", request.getRequestURI());
    }
}
