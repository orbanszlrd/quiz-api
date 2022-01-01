package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.GetUser;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<GetUser, EntityModel<GetUser>> {
    @Override
    public EntityModel<GetUser> toModel(GetUser getUser) {
        return EntityModel.of(getUser,
                linkTo(methodOn(UserRestController.class).findById(getUser.getId())).withSelfRel(),
                linkTo(methodOn(UserRestController.class).findAll()).withRel("users")
        );
    }
}
