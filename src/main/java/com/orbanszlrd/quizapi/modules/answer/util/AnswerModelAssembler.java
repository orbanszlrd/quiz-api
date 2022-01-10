package com.orbanszlrd.quizapi.modules.answer.util;

import com.orbanszlrd.quizapi.modules.answer.controller.AnswerRestController;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnswerModelAssembler implements RepresentationModelAssembler<AnswerResponse, EntityModel<AnswerResponse>> {
    @Override
    public EntityModel<AnswerResponse> toModel(AnswerResponse answerResponse) {
        return EntityModel.of(
                answerResponse,
                linkTo(methodOn(AnswerRestController.class).findById(answerResponse.getId())).withSelfRel(),
                linkTo(methodOn(AnswerRestController.class).findAll()).withRel("answers")
        );
    }
}
