package com.marketinginapp.startup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/loan")
public class LoansController {

    @GetMapping
    public Map<String, String> loans(){
        // ... business logic
        return Collections.singletonMap("message","loans");
    }
}
