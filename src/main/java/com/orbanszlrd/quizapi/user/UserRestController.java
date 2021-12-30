package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("")
    public List<GetUser> findAll() {
        return userService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public GetUser add(@RequestBody AddUser addUser) {
        return userService.add(addUser);
    }

    @PutMapping("/{id}")
    public GetUser update(@PathVariable Long id, @RequestBody UpdateUser updateUser) {
        return userService.update(id, updateUser);
    }

    @GetMapping("/{id}")
    public GetUser findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
