package com.orbanszlrd.quizapi.modules.question.error;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super(String.format("Question '%s' not found", id));
    }
}
