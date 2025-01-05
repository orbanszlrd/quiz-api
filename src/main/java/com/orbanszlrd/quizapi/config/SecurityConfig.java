package com.orbanszlrd.quizapi.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.orbanszlrd.quizapi.filter.JwtAuthenticationFilter;
import com.orbanszlrd.quizapi.filter.JwtAuthorizationFilter;
import com.orbanszlrd.quizapi.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final Algorithm algorithm;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), algorithm);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                        .requestMatchers("/h2-console/**").hasRole("OWNER")
                        .requestMatchers("/swagger-ui/**").hasRole("OWNER")
                        .requestMatchers("/api-docs").permitAll()
                        .requestMatchers("/user/quizzes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                        .requestMatchers("/api/v1/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/users/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/categories/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/quizzes/**").authenticated()
                        .requestMatchers("/quizzes/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/questions/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/answers/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/").permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtAuthorizationFilter(algorithm), UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
