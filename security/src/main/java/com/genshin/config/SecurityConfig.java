package com.genshin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .formLogin()
                .loginProcessingUrl("/login");
        http
                .authorizeHttpRequests()
                .requestMatchers("/status").permitAll()
                .anyRequest().authenticated();

        http
                .csrf()
                .disable();

        return http.build();
    }
}
