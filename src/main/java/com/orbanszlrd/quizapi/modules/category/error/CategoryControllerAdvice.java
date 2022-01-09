package com.orbanszlrd.quizapi.modules.category.error;

import com.orbanszlrd.quizapi.error.GenericProblem;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Status;

@ControllerAdvice
public class CategoryControllerAdvice {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<GenericProblem> notFoundException(CategoryNotFoundException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.NOT_FOUND));
    }
}
