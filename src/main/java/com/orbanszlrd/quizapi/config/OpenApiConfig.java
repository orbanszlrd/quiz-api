package com.orbanszlrd.quizapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Quiz API")
                        .description("Quiz API Documentation")
                        .version("v1.0.0")
                        .license(new License().name("MIT License").url("https://github.com/orbanszlrd/quiz-api/blob/main/LICENSE"))
                        .contact(new Contact().name("Szilárd Orbán").email("orbanszlrd@yahoo.com").url("http://quiz.dinodev.hu"))
                );
    }
}