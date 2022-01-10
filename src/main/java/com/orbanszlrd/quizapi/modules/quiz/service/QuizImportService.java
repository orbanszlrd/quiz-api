package com.orbanszlrd.quizapi.modules.quiz.service;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizImportService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    @Bean
    public CommandLineRunner quizImporter() {
        return args -> {
            Category category = categoryRepository.save(new Category("History"));

            List<Quiz> quizzes = List.of(
                    new Quiz("Ancient Egypt", 30, category),
                    new Quiz("Roman Empire", 30, category),
                    new Quiz("Aztec Empire", 30, category)
            );

            quizRepository.saveAll(quizzes);
        };
    }
}
