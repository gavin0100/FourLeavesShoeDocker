package com.data.filtro.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomExceptionFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException ex) {
            // Handle your custom exception here
            System.out.println("bat MyServletException trong CustomExceptionFilter");
            ((HttpServletResponse) servletResponse).sendRedirect("/logout_to_login/fromJwtEmptyOrNullException");
        }
    }
}
