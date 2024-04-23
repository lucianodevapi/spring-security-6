package com.marketinginapp.startup.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY = "myKey";
    private static final String API_KEY_HEADER = "api_key";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            final var apiKeiOptional = Optional.of(request.getHeader(API_KEY_HEADER));
            final var apiKey = apiKeiOptional.orElseThrow(() -> new BadCredentialsException("No header api key"));
            if(!apiKey.equals(API_KEY)){
               throw new BadCredentialsException("Invalid api key");
            }
        } catch (Exception exception){
            throw new BadCredentialsException("Invalid api key");
        }
        filterChain.doFilter(request, response);
    }
}
