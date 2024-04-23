package com.marketinginapp.startup.service;

import com.marketinginapp.startup.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByEmail(username).map(customer -> {
                    final var authorities = customer.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
                    return new User(customer.getEmail(), customer.getPassword(), authorities);
                }).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found by email: %s", username)));
    }
}
