package com.orbanszlrd.quizapi.modules.user;

import com.orbanszlrd.quizapi.modules.user.dto.UserResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>> {
    @Override
    public EntityModel<UserResponse> toModel(UserResponse userResponse) {
        return EntityModel.of(userResponse,
                linkTo(methodOn(UserRestController.class).findById(userResponse.getId())).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users")
        );
    }
}
