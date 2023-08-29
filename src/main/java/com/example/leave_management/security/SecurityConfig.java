package com.example.leave_management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/authenticate",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html")
                .permitAll()
                .requestMatchers("/api/v1/auth/register",
                       "/api/v1/auth/create-leave-type" )
                .hasAuthority("SUPERADMIN")

                .requestMatchers("/api/v1/Leave/pending-approval",
                        "/api/v1/Leave/pending-approval/**",
                        "/api/v1/auth/custom-leave-balance/**")
                .hasAnyAuthority("ADMIN", "SUPERADMIN")

                .requestMatchers("/api/v1/auth/changeUserName/**",
                        "api/v1/auth/changePassword/**",
                        "api/v1/auth/yearly-leave/**",
                        "api/v1/auth/paged",
                        "/api/v1/Leave/leave-application",
                        "/api/v1/Leave/Application-List/**",
                "api/v1/auth/get-leave-type")
                .hasAnyAuthority("EMPLOYEE", "ADMIN", "SUPERADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                ;


        return http.build();
    }

}


