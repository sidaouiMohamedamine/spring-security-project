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

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal
            (
                    @Nonnull HttpServletRequest request,
                    @Nonnull HttpServletResponse response,
                    @Nonnull FilterChain filterChain
            ) throws ServletException, IOException {
             final String authHeader =request.getHeader("Authorization");
             final String jwt;
             final String userEmail;
             if(authHeader== null || !authHeader.startsWith("Bearer ")){
                 filterChain.doFilter(request,response);
                 return;
             }
             /**
              * Now we will extract the token from the authentication header.
              * The header contains jeton kind and hashge algorithme
              ***/
            jwt=authHeader.substring(7);
            /**
             * todo extract the userEmail from JWT token.
             *  We need a class to manipulate the jwt.
             *  We named the class jwtService
             *  **/
            userEmail= jwtService.extractUsername(jwt);
    }
}
