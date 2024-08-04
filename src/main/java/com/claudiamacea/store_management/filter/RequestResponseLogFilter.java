package com.claudiamacea.store_management.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLogFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().toLowerCase().contains("/api/v1")) {
            response.setContentType("application/json");
            try {
                filterChain.doFilter(request, response);
            }
            finally {
                log.info("method: {} requestURI: {} contentType: {} response.status: {}",
                        request.getMethod(), request.getRequestURI(), response.getContentType(), response.getStatus());
            }
        }
        else {
            filterChain.doFilter(request, response);
        }


    }
}
