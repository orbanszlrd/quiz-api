package com.orbanszlrd.quizapi.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256("changeThisToSomethingSecure".getBytes());
    }
}
