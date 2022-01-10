package com.orbanszlrd.quizapi.modules.question.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private String text;
    private Integer timeLimit;
    private Byte value;
    private Long quizId;
    private String quizName;
}
