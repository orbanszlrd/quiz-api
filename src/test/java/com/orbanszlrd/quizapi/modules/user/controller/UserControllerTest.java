package com.orbanszlrd.quizapi.modules.user.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.orbanszlrd.quizapi.modules.user.controller.UserController;
import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.modules.user.model.dto.UpdateUserRequest;
import com.orbanszlrd.quizapi.modules.user.model.dto.UserResponse;
import com.orbanszlrd.quizapi.modules.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private Algorithm algorithm;

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(userService.findAll()).thenReturn(List.of(new UserResponse(1L, "user", "user@email.com", true, Role.USER)));

        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user@email.com")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(userService.count()).thenReturn(0L);

        this.mockMvc.perform(get("/users/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Save")));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(roles = "ADMIN")
    void details(Long id) throws Exception {
        when(userService.findById(id)).thenReturn(new UserResponse(id, "user" + id, "user" + id + "@email.com", true, Role.USER));

        this.mockMvc.perform(get("/users/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user" + id)));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(roles = "ADMIN")
    void edit(Long id) throws Exception {
        when(userService.findById(id)).thenReturn(new UserResponse(id, "user" + id, "user" + id + "@email.com", true, Role.USER));

        this.mockMvc.perform(get("/users/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user" + id)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void add() throws Exception {
        InsertUserRequest insertUserRequest = new InsertUserRequest("user", "user@email.com", "1234", Role.USER);
        insertUserRequest.setEnabled(true);
        UserResponse userResponse = new UserResponse();

        when(userService.add(insertUserRequest)).thenReturn(userResponse);

        this.mockMvc.perform(
                post("/users")
                        .param("username", insertUserRequest.getUsername())
                        .param("email", insertUserRequest.getEmail())
                        .param("password", insertUserRequest.getPassword())
                        .param("enabled", insertUserRequest.getEnabled().toString())
                        .param("role", insertUserRequest.getRole().toString())
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).add(insertUserRequest);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(roles = "ADMIN")
    void update(Long id) throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("user", "user@email.com", "1234", true, Role.USER);
        UserResponse userResponse = new UserResponse();

        when(userService.update(id, updateUserRequest)).thenReturn(userResponse);

        this.mockMvc.perform(
                put("/users/{id}", id)
                        .param("username", updateUserRequest.getUsername())
                        .param("email", updateUserRequest.getEmail())
                        .param("password", updateUserRequest.getPassword())
                        .param("enabled", updateUserRequest.getEnabled().toString())
                        .param("role", updateUserRequest.getRole().toString())
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).update(id, updateUserRequest);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @WithMockUser(roles = "ADMIN")
    void deleteById(Long id) throws Exception {
        doNothing().when(userService).deleteById(id);

        this.mockMvc.perform(delete("/users/{id}", id))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).deleteById(id);
    }
}