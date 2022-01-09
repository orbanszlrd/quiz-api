package com.orbanszlrd.quizapi.modules.question;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.quiz.Quiz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    private final QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
    private final QuestionService questionService = new QuestionService(questionRepository);

    private final Quiz quiz = new Quiz(1L, "Java Spring", 40, new Category(1L, "IT"));

    private final List<Question> questions = List.of(
            new Question(1L, "Question 1", 2, (byte) 5, quiz),
            new Question(2L, "Question 2", 4, (byte) 10, quiz)
    );

    @BeforeEach
    void setUp() {
        when(questionRepository.findAll()).thenReturn(questions);
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
    @ValueSource(ints = {1, 2})
    void update_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void findById_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void deleteById_works_fine() {
    }

    @Test
    void findByQuizId_works_fine() {
    }
}