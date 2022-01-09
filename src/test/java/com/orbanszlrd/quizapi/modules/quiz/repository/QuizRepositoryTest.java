package com.orbanszlrd.quizapi.modules.quiz.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("flyway-h2")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
class QuizRepositoryTest {
    @Autowired
    private QuizRepository quizRepository;

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    @DatabaseSetup("classpath:data/xml/insertQuizzes.xml")
    void findByCategoryId(Long id) {
        List<Quiz> quizzes = quizRepository.findByCategoryId(id);
        assertThat(quizzes).isNotNull();
        assertThat(quizzes.size()).isGreaterThan(0);

        for (Quiz quiz : quizzes) {
            assertThat(quiz.getCategory().getId()).isEqualTo(id);
        }
    }
}