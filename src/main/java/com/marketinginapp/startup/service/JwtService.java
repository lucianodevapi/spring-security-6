package com.marketinginapp.startup.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j

@Service
public class JwtService {

    public static final long JWT_TOKEN_VALIDITY = 60*60*1000;
    public static final String JWT_SECRET = "jxgEQe.XHuPq8VdbyYFNKAN.dudQ0903YUn4" ;


    public String generatedToken(UserDetails userDetails){
        final Map<String, Object> claims = Collections.singletonMap("ROLES",userDetails.getAuthorities().toString());
        return this.getToken(claims, userDetails.getUsername());
    }

    private Claims getAllClaimsToken(String token){
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                //.setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
        final var claims = this.getAllClaimsToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token){
        return this.getClaimsFromToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token){
        return this.getClaimsFromToken(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token){
        return this.getExpirationDateFromToken(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final var usernameFromUserDetails = userDetails.getUsername();
        final var usernameFromJwt = this.getUsernameFromToken(token);
        return (usernameFromUserDetails.equals(usernameFromJwt)) && !this.isTokenExpired(token);
    }

    private String getToken(Map<String, Object> claims, String subject){
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key)
                //.signWith(getSignatureKey())
                .compact();
    }

    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
