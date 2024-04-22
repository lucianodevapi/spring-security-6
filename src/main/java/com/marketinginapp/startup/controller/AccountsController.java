package com.marketinginapp.startup.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/account")
public class AccountsController {

    //@EnableMethodSecurity
    //@PreAuthorize("hasAnyAuthority('','')")
    @GetMapping
    public Map<String, String> accounts() {
        // ... business logic
        return Collections.singletonMap("message", "accounts");
    }
}
