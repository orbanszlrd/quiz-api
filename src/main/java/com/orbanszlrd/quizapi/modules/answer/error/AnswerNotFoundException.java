package com.orbanszlrd.quizapi.modules.answer.error;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(String.format("Answer '%s' not found!", id));
    }
}
