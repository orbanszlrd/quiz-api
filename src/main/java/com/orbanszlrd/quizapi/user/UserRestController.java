package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "The User API", description = "The User API")
public class UserRestController {
    private final UserService userService;

    @GetMapping(value = "", produces = {"application/json"})
    @Operation(summary = "List all users", description = "List all users")
    public List<GetUser> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "", produces = {"application/json"})
    @Operation(summary = "Insert a new user", description = "Insert a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public GetUser add(@Valid @RequestBody AddUser addUser) {
        return userService.add(addUser);
    }

    @PutMapping(value = "/{id}",  produces = {"application/json"})
    @Operation(summary = "Update a user", description = "Update a user by its id")
    public GetUser update(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id, @Valid @RequestBody UpdateUser updateUser) {
        return userService.update(id, updateUser);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @Operation(summary = "Find a user", description = "Find a user by its id")
    public GetUser findById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "The id of the user", required = true, example = "1") @Min(1) @PathVariable Long id) {
        userService.deleteById(id);
    }
}
