package com.orbanszlrd.quizapi.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.orbanszlrd.quizapi.filter.JwtAuthenticationFilter;
import com.orbanszlrd.quizapi.filter.JwtAuthorizationFilter;
import com.orbanszlrd.quizapi.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final Algorithm algorithm;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), algorithm);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").hasRole("OWNER")
                .antMatchers("/swagger-ui/**").hasRole("OWNER")
                .antMatchers("/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").authenticated()
                .antMatchers("/users/**").hasAnyRole("OWNER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                .antMatchers("/api/v1/**").hasAnyRole("OWNER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .and().httpBasic()
        ;

        http.addFilterBefore(new JwtAuthorizationFilter(algorithm), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(jwtAuthenticationFilter);
    }
}
