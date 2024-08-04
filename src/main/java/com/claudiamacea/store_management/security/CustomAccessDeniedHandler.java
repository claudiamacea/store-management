package com.claudiamacea.store_management.security;

import com.claudiamacea.store_management.handler.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        logger.error("Access Denied: ", accessDeniedException);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorCode(HttpServletResponse.SC_FORBIDDEN)
                .error(accessDeniedException.getMessage())
                .errorDescription("Access Denied")
                .validationErrors(Collections.emptySet())
                .build();

        String jsonResponse = mapper.writeValueAsString(exceptionResponse);
        response.getWriter().write(jsonResponse);
    }
}
