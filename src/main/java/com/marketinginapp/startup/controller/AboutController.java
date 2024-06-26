package com.marketinginapp.startup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/about")
public class AboutController {

    @GetMapping
    public Map<String, String> about() {
        // ... business logic
        return Collections.singletonMap("message", "about");
    }
}
