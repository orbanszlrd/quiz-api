package com.orbanszlrd.quizapi.modules.category.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/categories";
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from answer");
        jdbcTemplate.update("delete from question");
        jdbcTemplate.update("delete from quiz");
        jdbcTemplate.update("delete from category");
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}