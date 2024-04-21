package com.marketinginapp.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity

@SpringBootApplication
public class AppSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSecurityApplication.class, args);
	}

}
