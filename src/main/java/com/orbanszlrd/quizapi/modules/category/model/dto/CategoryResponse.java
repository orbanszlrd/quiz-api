package com.orbanszlrd.quizapi.modules.category.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    @Schema(description = "The id of the category", example = "1", required = true)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "The name of the category", example = "Programming", required = true)
    private String name;
}
