package com.orbanszlrd.quizapi.modules.question.service;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.repository.QuestionRepository;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionImportService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Bean
    public CommandLineRunner questionImporter() {
        return args -> {
            Category category = categoryRepository.save(new Category("Geography"));
            Quiz quiz = quizRepository.save(new Quiz("General Geography", 40, category));

            List<Question> questions = List.of(
                    new Question("What is the population of the world?", 1, (byte) 5, quiz),
                    new Question("Where is Hungary?", 2, (byte) 5, quiz),
                    new Question("What is the capital of Switzerland?", 1, (byte) 5, quiz)
            );

            questionRepository.saveAll(questions);
        };
    }
}
