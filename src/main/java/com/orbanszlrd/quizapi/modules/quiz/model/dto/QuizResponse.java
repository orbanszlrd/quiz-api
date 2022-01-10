package com.orbanszlrd.quizapi.modules.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Long id;
    private String name;
    private Integer timeLimit;
    private Long categoryId;
    private String categoryName;
}
