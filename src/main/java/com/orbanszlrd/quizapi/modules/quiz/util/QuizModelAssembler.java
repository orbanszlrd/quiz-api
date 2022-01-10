package com.orbanszlrd.quizapi.modules.quiz.util;

import com.orbanszlrd.quizapi.modules.quiz.controller.QuizRestController;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuizModelAssembler implements RepresentationModelAssembler<QuizResponse, EntityModel<QuizResponse>> {
    @Override
    public EntityModel<QuizResponse> toModel(QuizResponse quizResponse) {
        return EntityModel.of(
                quizResponse,
                linkTo(methodOn(QuizRestController.class).findById(quizResponse.getId())).withSelfRel(),
                linkTo(methodOn(QuizRestController.class).findAll()).withRel("categories")
        );
    }
}
