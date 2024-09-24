package com.data.filtro.authentication.exception;

import com.data.filtro.authentication.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomErrorHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Integer statusCode = (Integer) response.getStatus();
        if (statusCode != null && statusCode == 404) {
            response.sendRedirect("/404");
        } else {
            response.sendRedirect("/access-denied");
        }

    }
}
