package com.orbanszlrd.quizapi.modules.quiz.controller;

import com.orbanszlrd.quizapi.modules.category.controller.CategoryRestController;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizRequest;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import com.orbanszlrd.quizapi.modules.quiz.util.QuizModelAssembler;
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
@RequestMapping("/api/v1/quizzes")
@Tag(name = "The Quiz API", description = "The Quiz API")
@RequiredArgsConstructor
public class QuizRestController {
    private final QuizService quizService;
    private final QuizModelAssembler quizModelAssembler;

    @GetMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "List all quizzes", description = "List all quizzes")
    public CollectionModel<EntityModel<QuizResponse>> findAll() {
        List<QuizResponse> quizResponses = quizService.findAll();

        List<EntityModel<QuizResponse>> quizEntities = quizResponses.stream().map(quizModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                quizEntities,
                linkTo(methodOn(QuizRestController.class).findAll()).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users"),
                linkTo(methodOn(CategoryRestController.class).findAll()).withRel("categories")
        );
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a quiz", description = "Find a quiz by its id")
    public EntityModel<QuizResponse> findById(@PathVariable Long id) {
        return quizModelAssembler.toModel(quizService.findById(id));
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new quiz", description = "Insert a new quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<QuizResponse> add(@RequestBody QuizRequest quizRequest) {
        QuizResponse quizResponse = quizService.add(quizRequest);
        return quizModelAssembler.toModel(quizResponse);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a quiz", description = "Update a quiz by its id")
    public EntityModel<QuizResponse> update(@PathVariable Long id, @RequestBody QuizRequest quizRequest) {
        QuizResponse quizResponse = quizService.update(id, quizRequest);
        return quizModelAssembler.toModel(quizResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a quiz", description = "Delete a quiz by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        quizService.deleteById(id);
    }
}
