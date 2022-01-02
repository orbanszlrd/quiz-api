package com.orbanszlrd.quizapi.modules.quiz;

import com.orbanszlrd.quizapi.modules.category.Category;
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
class QuizServiceTest {
    private final QuizRepository quizRepository = Mockito.mock(QuizRepository.class);
    private final QuizService quizService = new QuizService(quizRepository);

    private final Category category = new Category("IT");

    private final List<Quiz> quizzes = List.of(
            new Quiz(1L, "Angular Basics", 30, category),
            new Quiz(2L, "Java Intermediate", 40, category),
            new Quiz(3L,"PHP Advanced", 60, category)
    );

    @BeforeEach
    void setUp() {
        when(quizRepository.findAll()).thenReturn(quizzes);
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