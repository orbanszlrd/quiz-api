package com.orbanszlrd.quizapi.modules.answer.service;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.answer.repository.AnswerRepository;
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
public class AnswerImportService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Bean
    public CommandLineRunner answerImporter() {
        return args -> {
            Category category = categoryRepository.save(new Category("Mathematics"));
            Quiz quiz = quizRepository.save(new Quiz("General Math for Beginners", 40, category));
            Question question = questionRepository.save(new Question("How much is  1 + 1 ?", 1, (byte) 5, quiz));

            List<Answer> answers = List.of(
                    new Answer("1", false, question),
                    new Answer("2", true, question),
                    new Answer("3", false, question),
                    new Answer("4", false, question)
            );

            answerRepository.saveAll(answers);
        };
    }
}
