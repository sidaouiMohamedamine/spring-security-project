package com.sid.springSecurity_Project.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//the annotation below will create a constructor using any final field declared
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal
            (
                    @Nonnull HttpServletRequest request,
                    @Nonnull HttpServletResponse response,
                    @Nonnull FilterChain filterChain
            ) throws ServletException, IOException {

    }
}
