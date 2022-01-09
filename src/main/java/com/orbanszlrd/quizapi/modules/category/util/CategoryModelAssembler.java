package com.orbanszlrd.quizapi.modules.category.util;

import com.orbanszlrd.quizapi.modules.category.controller.CategoryRestController;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<CategoryResponse, EntityModel<CategoryResponse>> {
    @Override
    public EntityModel<CategoryResponse> toModel(CategoryResponse categoryResponse) {
        return EntityModel.of(
                categoryResponse,
                linkTo(methodOn(CategoryRestController.class).findById(categoryResponse.getId())).withSelfRel(),
                linkTo(methodOn(CategoryRestController.class).findAll()).withRel("categories")
        );
    }
}
