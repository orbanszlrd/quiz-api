package com.orbanszlrd.quizapi.modules.answer.controller;

import com.orbanszlrd.quizapi.modules.category.controller.CategoryRestController;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerRequest;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerResponse;
import com.orbanszlrd.quizapi.modules.answer.service.AnswerService;
import com.orbanszlrd.quizapi.modules.answer.util.AnswerModelAssembler;
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
@RequestMapping("/api/v1/answers")
@Tag(name = "The Answer API", description = "The Answer API")
@RequiredArgsConstructor
public class AnswerRestController {
    private final AnswerService answerService;
    private final AnswerModelAssembler answerModelAssembler;

    @GetMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "List all answers", description = "List all answers")
    public CollectionModel<EntityModel<AnswerResponse>> findAll() {
        List<AnswerResponse> answerResponses = answerService.findAll();

        List<EntityModel<AnswerResponse>> answerEntities = answerResponses.stream().map(answerModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                answerEntities,
                linkTo(methodOn(AnswerRestController.class).findAll()).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users"),
                linkTo(methodOn(CategoryRestController.class).findAll()).withRel("categories"),
                linkTo(methodOn(QuizRestController.class).findAll()).withRel("quizzes"),
                linkTo(methodOn(QuizRestController.class).findAll()).withRel("questions")
        );
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a answer", description = "Find a answer by its id")
    public EntityModel<AnswerResponse> findById(@PathVariable Long id) {
        return answerModelAssembler.toModel(answerService.findById(id));
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new answer", description = "Insert a new answer")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<AnswerResponse> add(@RequestBody AnswerRequest answerRequest) {
        AnswerResponse answerResponse = answerService.add(answerRequest);
        return answerModelAssembler.toModel(answerResponse);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a answer", description = "Update a answer by its id")
    public EntityModel<AnswerResponse> update(@PathVariable Long id, @RequestBody AnswerRequest answerRequest) {
        AnswerResponse answerResponse = answerService.update(id, answerRequest);
        return answerModelAssembler.toModel(answerResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a answer", description = "Delete a answer by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        answerService.deleteById(id);
    }
}
