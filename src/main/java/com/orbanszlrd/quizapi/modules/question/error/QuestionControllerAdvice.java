package com.orbanszlrd.quizapi.modules.question.error;

import com.orbanszlrd.quizapi.error.GenericProblem;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zalando.problem.Status;

@ControllerAdvice
@ResponseBody
public class QuestionControllerAdvice {
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<GenericProblem> notFoundException(QuestionNotFoundException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(new GenericProblem(e, Status.NOT_FOUND));
    }
}
