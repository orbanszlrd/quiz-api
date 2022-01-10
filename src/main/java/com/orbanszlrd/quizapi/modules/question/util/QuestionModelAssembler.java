package com.orbanszlrd.quizapi.modules.question.util;

import com.orbanszlrd.quizapi.modules.question.controller.QuestionRestController;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionModelAssembler implements RepresentationModelAssembler<QuestionResponse, EntityModel<QuestionResponse>> {
    @Override
    public EntityModel<QuestionResponse> toModel(QuestionResponse questionResponse) {
        return EntityModel.of(
                questionResponse,
                linkTo(methodOn(QuestionRestController.class).findById(questionResponse.getId())).withSelfRel(),
                linkTo(methodOn(QuestionRestController.class).findAll()).withRel("questions")
        );
    }
}
