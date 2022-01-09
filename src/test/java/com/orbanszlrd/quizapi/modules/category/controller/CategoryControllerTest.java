package com.orbanszlrd.quizapi.modules.category.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import com.orbanszlrd.quizapi.modules.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private Algorithm algorithm;

    @Test
    @WithMockUser(roles = "USER")
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void edit() {
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