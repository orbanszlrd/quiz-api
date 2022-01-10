package com.orbanszlrd.quizapi.modules.quiz.error;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(Long id) {
        super(String.format("Quiz '%s' not found!", id));
    }
}
