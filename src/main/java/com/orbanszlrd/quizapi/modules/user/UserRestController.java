package com.orbanszlrd.quizapi.modules.user;

import com.orbanszlrd.quizapi.modules.user.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.modules.user.dto.UserResponse;
import com.orbanszlrd.quizapi.modules.user.dto.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "The User API", description = "The User API")
public class UserRestController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @GetMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "List all users", description = "List all users")
    public CollectionModel<EntityModel<UserResponse>> findAll() {
        List<UserResponse> userResponses = userService.findAll();
        List<EntityModel<UserResponse>> userEntities = userResponses.stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(userEntities,
                linkTo(methodOn(UserRestController.class).findAll()).withSelfRel()
        );
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new user", description = "Insert a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserResponse> add(@Valid @RequestBody InsertUserRequest insertUserRequest) {
        UserResponse userResponse = userService.add(insertUserRequest);
        return userModelAssembler.toModel(userResponse);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a user", description = "Update a user by its id")
    public EntityModel<UserResponse> update(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse userResponse = userService.update(id, updateUserRequest);

        return userModelAssembler.toModel(userResponse);
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a user", description = "Find a user by its id")
    public EntityModel<UserResponse> findById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return userModelAssembler.toModel(userResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        userService.deleteById(id);
    }
}
