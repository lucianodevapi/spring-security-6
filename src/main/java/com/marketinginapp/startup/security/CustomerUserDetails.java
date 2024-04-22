package com.marketinginapp.startup.security;

import com.marketinginapp.startup.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@RequiredArgsConstructor
//
//@Transactional
//@Service
public class CustomerUserDetails
       // implements UserDetailsService
{

//    private final CustomerRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return repository
//                .findByEmail(username)
//                .map(customer -> {
//                    var authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
//                    return new User(customer.getEmail(), customer.getPassword(), authorities);
//                }).orElseThrow(() -> new UsernameNotFoundException(
//                        String.format("User not found by email: %s", username)));
//    }
}
