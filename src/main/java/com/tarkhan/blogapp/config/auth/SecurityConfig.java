package com.tarkhan.blogapp.config.auth;

import com.tarkhan.blogapp.service.auth.UserDetailsServiceImpl;
import com.tarkhan.blogapp.service.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserDetailsServiceImpl userDetailsServiceImpl
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/register", "/swagger-ui/**",
                                "/swagger-ui.html", "/v3/api-docs/**",
                                "/api/v1/auth/login"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/auth/account", "/api/v1/auth/update",
                                "api/v1/auth/changePassword", "/api/v1/profiles/**",
                                "/api/v1/posts/**"
                        ).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/v1/categories/**", "/api/v1/tags/**")
                        .hasAnyAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
