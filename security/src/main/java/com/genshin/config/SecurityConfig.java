package com.genshin.config;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.genshin.dao.UserProfile;
import com.genshin.exception.GenerateTokenFailException;
import com.genshin.filter.JWTAuthenticationFilter;
import com.genshin.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler());
        http
                .authorizeHttpRequests()
                .requestMatchers("/test/status").permitAll()
                .requestMatchers("/users/register").permitAll()
                .requestMatchers("/test/login").hasAuthority("admin")
                .requestMatchers("/artifact/insert").hasAnyAuthority("normal","admin")
                .anyRequest().authenticated();


        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationSuccessHandler customLoginSuccessHandler() {
        return (request, response, authentication) -> {
            // run custom logics upon successful login
            UserProfile userProfile = (UserProfile) authentication.getPrincipal();
            List<String> list = new LinkedList<>();
            Collection<? extends GrantedAuthority> collection = userProfile.getAuthorities();
            if (collection == null) {
                list.add("normal");
            } else {
                for (GrantedAuthority grantedAuthority : collection) {
                    list.add(grantedAuthority.getAuthority());
                }
            }


            try {
            } catch (Exception e) {
                throw new GenerateTokenFailException();
            }


            Map<String, String> responseBody = Collections.singletonMap("token", JWTUtils.generateToken(userProfile.getUsername(), list));
            PrintWriter printWriter = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonOutput = new ObjectMapper().writeValueAsString(responseBody);
            printWriter.println(jsonOutput);
            printWriter.flush();
            printWriter.close();
        };

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

}
