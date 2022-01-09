package com.orbanszlrd.quizapi.modules.category.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @Schema(description = "The name of the category", example = "Programming", required = true)
    private String name;
}
