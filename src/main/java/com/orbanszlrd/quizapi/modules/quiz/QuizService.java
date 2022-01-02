package com.orbanszlrd.quizapi.modules.quiz;

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

    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz update(Long id, Quiz quiz) {
        quiz.setId(id);
        return quizRepository.save(quiz);
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
