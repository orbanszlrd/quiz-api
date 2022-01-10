package com.orbanszlrd.quizapi.modules.question.controller;

import com.orbanszlrd.quizapi.modules.category.controller.CategoryRestController;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionRequest;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
import com.orbanszlrd.quizapi.modules.question.util.QuestionModelAssembler;
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
@RequestMapping("/api/v1/questions")
@Tag(name = "The Question API", description = "The Question API")
@RequiredArgsConstructor
public class QuestionRestController {
    private final QuestionService questionService;
    private final QuestionModelAssembler questionModelAssembler;

    @GetMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "List all questions", description = "List all questions")
    public CollectionModel<EntityModel<QuestionResponse>> findAll() {
        List<QuestionResponse> questionResponses = questionService.findAll();

        List<EntityModel<QuestionResponse>> questionEntities = questionResponses.stream().map(questionModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                questionEntities,
                linkTo(methodOn(QuestionRestController.class).findAll()).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users"),
                linkTo(methodOn(CategoryRestController.class).findAll()).withRel("categories"),
                linkTo(methodOn(QuizRestController.class).findAll()).withRel("quizzes")
        );
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a question", description = "Find a question by its id")
    public EntityModel<QuestionResponse> findById(@PathVariable Long id) {
        return questionModelAssembler.toModel(questionService.findById(id));
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new question", description = "Insert a new question")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<QuestionResponse> add(@RequestBody QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.add(questionRequest);
        return questionModelAssembler.toModel(questionResponse);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a question", description = "Update a question by its id")
    public EntityModel<QuestionResponse> update(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.update(id, questionRequest);
        return questionModelAssembler.toModel(questionResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a question", description = "Delete a question by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        questionService.deleteById(id);
    }
}
