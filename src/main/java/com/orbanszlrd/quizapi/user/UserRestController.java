package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
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
    public CollectionModel<EntityModel<GetUser>> findAll() {
        List<GetUser> getUsers = userService.findAll();
        List<EntityModel<GetUser>> userEntities = getUsers.stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(userEntities,
                linkTo(methodOn(UserRestController.class).findAll()).withSelfRel()
        );
    }

    @PostMapping(value = "", produces = {"application/hal+json"})
    @Operation(summary = "Insert a new user", description = "Insert a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GetUser> add(@Valid @RequestBody AddUser addUser) {
        GetUser getUser = userService.add(addUser);
        return userModelAssembler.toModel(getUser);
    }

    @PutMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Update a user", description = "Update a user by its id")
    public EntityModel<GetUser> update(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id, @Valid @RequestBody UpdateUser updateUser) {
        GetUser getUser = userService.update(id, updateUser);

        return userModelAssembler.toModel(getUser);
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    @Operation(summary = "Find a user", description = "Find a user by its id")
    public EntityModel<GetUser> findById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        GetUser getUser = userService.findById(id);
        return userModelAssembler.toModel(getUser);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        userService.deleteById(id);
    }
}
