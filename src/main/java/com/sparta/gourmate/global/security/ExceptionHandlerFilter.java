package com.sparta.gourmate.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.gourmate.global.exception.CustomErrorResponse;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            setErrorResponse(ex.getErrorCode(), response, ex);
        } catch (RuntimeException ex) {
            setErrorResponse(ErrorCode.COMMON_SERVER_ERROR, response, ex);
        }
    }

    private void setErrorResponse(ErrorCode errorCode, HttpServletResponse response, Throwable ex) throws IOException {
        log.error(ex.getMessage(), ex);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Encoding.DEFAULT_CHARSET.name());
        response.setStatus(errorCode.getHttpStatus().value());
        String responseData = new ObjectMapper().writeValueAsString(CustomErrorResponse.of(errorCode));
        response.getWriter().write(responseData);
    }
}
