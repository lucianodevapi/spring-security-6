package com.marketinginapp.startup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@EnableWebSecurity(debug = true)
//@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        var requestHeader = new CsrfTokenRequestAttributeHandler();
        requestHeader.setCsrfRequestAttributeName("_csrf");

        return http
                .addFilterBefore(new ApiKeyFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth
//                                .requestMatchers("/loan").hasAuthority("VIEW_LOANS")
//                                .requestMatchers("/balance").hasAuthority("VIEW_BALANCE")
//                                .requestMatchers("/card").hasAuthority("VIEW_CARDS")
//                                .requestMatchers("/account").hasAnyAuthority("VIEW_ACCOUNT","VIEW_CARDS")

                                .requestMatchers("/loan").hasRole("VIEW_LOANS")
                                .requestMatchers("/balance").hasRole("VIEW_BALANCE")
                                .requestMatchers("/card").hasRole("VIEW_CARDS")
                                .requestMatchers("/account").hasAnyRole("VIEW_ACCOUNT","VIEW_CARDS")

                                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .cors(cors -> corsConfigurationSource())
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(requestHeader)
                        .ignoringRequestMatchers("/welcome","/about")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class )
                .build();
    }

//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        var admin = User
//                .withUsername("admin")
//                .password("to_be_encoder")
//                .authorities("ADMIN")
//                .build();
//
//        var user = User
//                .withUsername("user")
//                .password("to_be_encoder")
//                .authorities("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//    @Bean
//    UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        var config = new CorsConfiguration();
//        config.setAllowedOrigins(
//                List.of(
//                        "http://localhost:4200",
//                        "http://my_app"
//                )
//        );
        config.setAllowedOrigins(List.of("*"));

//        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
