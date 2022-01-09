package com.orbanszlrd.quizapi.modules.category.error;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category '%s' not found!", id));
    }
}
