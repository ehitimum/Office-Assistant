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
                        "/api/v1/auth/register",
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
                .requestMatchers(
                       "/api/v1/auth/create-leave-type",
                        "/api/v1/auth/delete-user/**")
                .hasAuthority("SUPERADMIN")

                .requestMatchers("/api/v1/Leave/pending-approval",
                        "/api/v1/Leave/pending-approval/**",
                        "/api/v1/auth/custom-leave-balance/**",
                        "api/v1/holidays/create-holidays",
                        "api/v1/holidays/link-holidays/users/**",
                        "api/v1/holidays/update-holiday-info/**")
                .hasAnyAuthority("ADMIN", "SUPERADMIN")

                .requestMatchers("/api/v1/auth/changeUserName/**",
                        "api/v1/auth/changePassword/**",
                        "api/v1/auth/yearly-leave/**",
                        "api/v1/auth/user-list",
                        "/api/v1/Leave/leave-application",
                        "/api/v1/Leave/Application-List/**",
                        "api/v1/auth/get-leave-type",
                        "api/v1/holidays/show-HolidayList")
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


