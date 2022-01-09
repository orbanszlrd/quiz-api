package com.orbanszlrd.quizapi.modules.answer;

import com.orbanszlrd.quizapi.modules.question.Question;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
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
class AnswerServiceTest {
    private final AnswerRepository answerRepository = Mockito.mock(AnswerRepository.class);
    private final AnswerService answerService = new AnswerService(answerRepository);

    private final Question question = new Question(1L, "Question 1", 2, (byte) 10, new Quiz());
    private final List<Answer> answers = List.of(
            new Answer(1L, "Answer A", false, question),
            new Answer(1L, "Answer B", false, question),
            new Answer(1L, "Answer C", true, question),
            new Answer(1L, "Answer D", false, question)
    );

    @BeforeEach
    void setUp() {
        when(answerRepository.findAll()).thenReturn(answers);
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
    @ValueSource(ints = {1, 2, 3, 4})
    void update_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void findById_works_fine() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void deleteById_works_fine() {
    }

    @Test
    void findByQuestionId_works_fine() {
    }

    @Test
    void findCorrectAnswersByQuestionId_works_fine() {
    }
}