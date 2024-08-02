package com.claudiamacea.store_management.security;

import com.claudiamacea.store_management.handler.ExceptionResponse;
import com.claudiamacea.store_management.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        logger.error("Bad credentials exception occurred: ", authException);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorCode(HttpServletResponse.SC_UNAUTHORIZED)
                .error(authException.getMessage())
                .errorDescription("Invalid username or password")
                .validationErrors(Collections.emptySet())
                .build();

        String jsonResponse = mapper.writeValueAsString(exceptionResponse);
        response.getWriter().write(jsonResponse);
    }
}