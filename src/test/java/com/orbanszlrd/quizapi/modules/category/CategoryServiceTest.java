package com.orbanszlrd.quizapi.modules.category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private final CategoryService categoryService = new CategoryService(categoryRepository);

    private final List<Category> categories = List.of(
            new Category(1L, "Astronomy"),
            new Category("Biology"),
            new Category("Geography"),
            new Category("IT"),
            new Category("Literature")
    );

    @BeforeEach
    void setUp() {
        when(categoryRepository.findAll()).thenReturn(categories);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll_works_fine() {
    }

    @Test
    void add_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void update_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findById_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void deleteById_works_fine() {
    }
}