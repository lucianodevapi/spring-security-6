package com.marketinginapp.startup.security;

import com.marketinginapp.startup.service.JwtService;
import com.marketinginapp.startup.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            jwt = requestTokenHeader.substring(7);
            try {
                log.info(jwt);
                // TODO: VALIDATE TOKEN
                username = jwtService.getUsernameFromToken(jwt);

            } catch (IllegalArgumentException exception) {
                log.error(exception.getLocalizedMessage());

            } catch (ExpiredJwtException exception){
                log.warn(exception.getMessage());
            }
        }
        if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
            final var userDetails =  this.jwtUserDetailsService.loadUserByUsername(username);
            if(this.jwtService.validateToken(jwt, userDetails)){
                var usernameAndPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernameAndPasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
