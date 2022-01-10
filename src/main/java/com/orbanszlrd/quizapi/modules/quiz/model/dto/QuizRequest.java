package com.orbanszlrd.quizapi.modules.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {
    private String name;
    private Integer timeLimit;
    private Long categoryId;
}
