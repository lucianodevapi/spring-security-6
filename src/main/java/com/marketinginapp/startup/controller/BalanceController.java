package com.marketinginapp.startup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/balance")
public class BalanceController {

    @GetMapping
    public Map<String, String> balance() {
        // ... business logic
        return Collections.singletonMap("message", "balance");
    }
}
