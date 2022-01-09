package com.orbanszlrd.quizapi.modules.quiz.service;

import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public List<Quiz> findByCategoryId(Long categoryId) {
        return quizRepository.findByCategoryId(categoryId);
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz update(Long id, Quiz quiz) {
        quiz.setId(id);
        return quizRepository.save(quiz);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }

    public long count() {
        return quizRepository.count();
    }
}
