package com.orbanszlrd.quizapi.modules.answer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    private Long id;
    private String text;
    private boolean correct;
    private Long questionId;
    private String questionText;
}
