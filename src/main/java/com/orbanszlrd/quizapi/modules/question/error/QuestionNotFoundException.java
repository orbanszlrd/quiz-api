package com.orbanszlrd.quizapi.modules.question.error;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super(String.format("Question '%20' not found", id));
    }
}
