package com.example.demo.student.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class WebSecurityConfig {


    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/publico/**")).permitAll()
    }

    studentDeta

}
