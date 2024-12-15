package com.systemweb.webmapsystem.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Update SessionValidationFilter.java
@Component
public class SessionValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");

        if (!isPublicResource(httpRequest.getRequestURI())) {
            HttpSession session = httpRequest.getSession(false);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (session == null || auth == null || !auth.isAuthenticated()) {
                httpResponse.sendRedirect("/login?expired");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isPublicResource(String path) {
        return path.startsWith("/login") || 
               path.startsWith("/css/") ||
               path.startsWith("/image/") ||
               path.startsWith("/Register") ||
               path.startsWith("/NewPassword");
    }
}