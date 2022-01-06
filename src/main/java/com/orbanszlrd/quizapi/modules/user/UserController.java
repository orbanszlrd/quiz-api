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

    @GetMapping("/create")
    public String create(Model model) {
        Long postfix = userService.count() + 1;

        UserResponse user = new UserResponse();
        user.setUsername("user" + postfix);
        user.setEmail("user" + postfix + "@email.com");
        user.setEnabled(true);
        user.setRole(Role.USER);
        user.setGender(Gender.OTHER);

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("action", "/users");
        model.addAttribute("method", "POST");

        return "user/edit-user";
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
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("action", "/users/" + id);
        model.addAttribute("method", "PUT");

        return "user/edit-user";
    }

    @PostMapping("")
    public String add(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam("role") Role role,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "dateOfBirth", required = false, defaultValue = "1900-01-01") Date dateOfBirth,
            @RequestParam(value = "gender", required = false) Gender gender
    ) {
        InsertUserRequest request = new InsertUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        request.setRole(role);
        request.setEnabled(enabled);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setGender(gender);

        if (!dateOfBirth.equals(Date.valueOf("1900-01-01"))) {
            request.setDateOfBirth(dateOfBirth);
        }

        userService.add(request);

        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("username") String username,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam(value = "enabled", required = false) Boolean enabled,
                         @RequestParam("role") Role role,
                         @RequestParam(value = "firstName", required = false) String firstName,
                         @RequestParam(value = "lastName", required = false) String lastName,
                         @RequestParam(value = "dateOfBirth", required = false, defaultValue = "1900-01-01") Date dateOfBirth,
                         @RequestParam(value = "gender", required = false) Gender gender
    ) {

        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        request.setEnabled(enabled);
        request.setRole(role);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setGender(gender);

        if (!dateOfBirth.equals(Date.valueOf("1900-01-01"))) {
            request.setDateOfBirth(dateOfBirth);
        }

        userService.update(id, request);

        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);

        return "redirect:/users";
    }
}
