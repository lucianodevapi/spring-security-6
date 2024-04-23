package com.marketinginapp.startup.controller;

import com.marketinginapp.startup.dto.request.JwtRequest;
import com.marketinginapp.startup.dto.response.JwtResponse;
import com.marketinginapp.startup.service.JwtService;
import com.marketinginapp.startup.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtService jwtService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> postToken(@RequestBody JwtRequest request){
        this.authenticate(request);
        final var userDetails = this.jwtUserDetailsService.loadUserByUsername(request.username());
        final String token = this.jwtService.generatedToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
    }

    private void authenticate(JwtRequest request){
        try{
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException | DisabledException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
