package com.orbanszlrd.quizapi.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zalando.problem.Status;

@ControllerAdvice
@ResponseBody
public class GlobalControllerAdvice {
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<GenericProblem> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.BAD_REQUEST));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericProblem> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.BAD_REQUEST));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<GenericProblem> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.NOT_FOUND));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<GenericProblem> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.BAD_REQUEST));
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<GenericProblem> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.NOT_FOUND));
    }
}
