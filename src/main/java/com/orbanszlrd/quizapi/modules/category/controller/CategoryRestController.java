package com.orbanszlrd.quizapi.modules.category.controller;

import com.orbanszlrd.quizapi.modules.answer.controller.AnswerRestController;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryRequest;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import com.orbanszlrd.quizapi.modules.category.util.CategoryModelAssembler;
import com.orbanszlrd.quizapi.modules.question.controller.QuestionRestController;
import com.orbanszlrd.quizapi.modules.quiz.controller.QuizRestController;
import com.orbanszlrd.quizapi.modules.user.controller.UserRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "The Category API", description = "The Category API")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;
    private final CategoryModelAssembler categoryModelAssembler;

    @GetMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "List all categories", description = "List all categories")
    public CollectionModel<EntityModel<CategoryResponse>> findAll() {
        List<CategoryResponse> categoryResponses = categoryService.findAll();

        List<EntityModel<CategoryResponse>> categoryEntities = categoryResponses.stream().map(categoryModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                categoryEntities,
                linkTo(methodOn(CategoryRestController.class).findAll()).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users"),
                linkTo(methodOn(QuizRestController.class).findAll()).withRel("quizzes"),
                linkTo(methodOn(QuestionRestController.class).findAll()).withRel("questions"),
                linkTo(methodOn(AnswerRestController.class).findAll()).withRel("answers")
        );
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a category", description = "Find a category by its id")
    public EntityModel<CategoryResponse> findById(@PathVariable Long id) {
        return categoryModelAssembler.toModel(categoryService.findById(id));
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new category", description = "Insert a new category")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<CategoryResponse> add(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.add(categoryRequest);
        return categoryModelAssembler.toModel(categoryResponse);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a category", description = "Update a category by its id")
    public EntityModel<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.update(id, categoryRequest);
        return categoryModelAssembler.toModel(categoryResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
