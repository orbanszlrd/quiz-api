package com.orbanszlrd.quizapi.modules.user;

import com.orbanszlrd.quizapi.modules.user.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.modules.user.dto.UpdateUserRequest;
import com.orbanszlrd.quizapi.modules.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String findAll(Model model) {
        List<UserResponse> users = userService.findAll();

        model.addAttribute("users", users);

        return "user/users";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        UserResponse user = userService.findById(id);

        model.addAttribute("user", user);

        return "user/user-details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        UserResponse user = userService.findById(id);

        model.addAttribute("user", user);

        return "user/edit-user";
    }

    @PostMapping("")
    public String add(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") Role role,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") Date dateOfBirth,
            @RequestParam("gender") Gender gender
    ) {
        InsertUserRequest request = new InsertUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        request.setRole(role);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDateOfBirth(dateOfBirth);
        request.setGender(gender);

        userService.add(request);

        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("username") String username,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("enabled") Boolean enabled,
                         @RequestParam("role") Role role,
                         @RequestParam("firstName") String firstName,
                         @RequestParam("lastName") String lastName,
                         @RequestParam("dateOfBirth") Date dateOfBirth,
                         @RequestParam("gender") Gender gender
    ) {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        request.setEnabled(enabled);
        request.setRole(role);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDateOfBirth(dateOfBirth);
        request.setGender(gender);

        userService.update(id, request);

        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);

        return "redirect:/users";
    }
}
