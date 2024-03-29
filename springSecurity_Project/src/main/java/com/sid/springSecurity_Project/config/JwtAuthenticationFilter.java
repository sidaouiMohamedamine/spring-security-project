package com.sid.springSecurity_Project.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//the annotation below will create a constructor using any final field declared
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
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
            if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication() == null){
                /**Get the user from database***/
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                            );
                            authToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
    }
}
