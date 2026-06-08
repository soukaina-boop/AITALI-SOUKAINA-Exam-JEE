package org.aitali.soukaina.examjee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/clients", "/api/clients/**").hasAnyRole("CLIENT", "EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/clients", "/api/clients/**").hasAnyRole("EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clients", "/api/clients/**").hasAnyRole("EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clients", "/api/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/credits", "/api/credits/**").hasAnyRole("CLIENT", "EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/credits", "/api/credits/**").hasAnyRole("EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/credits", "/api/credits/**").hasAnyRole("EMPLOYE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/credits", "/api/credits/**").hasRole("ADMIN")
                        .requestMatchers("/api/remboursements", "/api/remboursements/**").hasAnyRole("EMPLOYE", "ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
