package com.simplesdental.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/api/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/api/v2/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/products/**").hasAuthority("admin")
                        .anyRequest().authenticated()
                ).cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
