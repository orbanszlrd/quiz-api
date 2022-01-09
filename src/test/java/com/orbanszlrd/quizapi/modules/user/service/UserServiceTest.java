package com.orbanszlrd.quizapi.modules.user.service;

import com.orbanszlrd.quizapi.modules.user.error.UserNotFoundException;
import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.modules.user.model.dto.UserResponse;
import com.orbanszlrd.quizapi.modules.user.model.dto.UpdateUserRequest;
import com.orbanszlrd.quizapi.modules.user.model.User;
import com.orbanszlrd.quizapi.modules.user.repository.UserRepository;
import com.orbanszlrd.quizapi.modules.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(passwordEncoder, modelMapper, userRepository);

    private final List<User> users = List.of(
            new User(1L, "tom", "tom@email.com", passwordEncoder.encode("tom"), true, Role.ADMIN),
            new User(2L, "jim", "jim@email.com", passwordEncoder.encode("jim"), false, Role.USER),
            new User(3L, "ben", "ben@email.com", passwordEncoder.encode("ben"), true, Role.USER)
    );

    @ParameterizedTest
    @ValueSource(strings = {"tom", "jim", "ben"})
    void loadUserByUsername_works_fine(String username) {
        Optional<User> userOptional = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
        User user  = userOptional.orElseThrow(RuntimeException::new);
        when(userRepository.findByUsername(username)).thenReturn(userOptional);
        UserDetails userDetails = userService.loadUserByUsername(username);
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.isEnabled()).isEqualTo(user.isEnabled());
    }

    @Test
    void findAll_works_fine() {
        when(userRepository.findAll()).thenReturn(users);
        List<UserResponse> userResponses = userService.findAll();
        assertThat(userResponses.size()).isEqualTo(users.size());
    }

    @Test
    void add_works_fine() {
        InsertUserRequest insertUserRequest = new InsertUserRequest("joe", "joe@email.com", passwordEncoder.encode("joe"), Role.ADMIN);
        UserResponse userResponse = userService.add(insertUserRequest);
        assertThat(userResponse.getUsername()).isEqualTo(insertUserRequest.getUsername());
        assertThat(userResponse.getEmail()).isEqualTo(insertUserRequest.getEmail());
        assertThat(userResponse.getRole()).isEqualTo(insertUserRequest.getRole());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void update_works_fine(long id) {
        when(userRepository.findById(id)).thenReturn(users.stream().filter(user -> user.getId() == id).findFirst());

        UpdateUserRequest updateUserRequest = new UpdateUserRequest("bill", "bill@email.com", "bill", false, Role.ADMIN);
        UserResponse userResponse = userService.update(id, updateUserRequest);

        assertThat(userResponse.getUsername()).isEqualTo(updateUserRequest.getUsername());
        assertThat(userResponse.getEmail()).isEqualTo(updateUserRequest.getEmail());
        assertThat(userResponse.getRole()).isEqualTo(updateUserRequest.getRole());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findById_works_fine(long id) {
        Optional<User> userOptional = users.stream().filter(user -> user.getId() == id).findFirst();
        User user = userOptional.orElseThrow(RuntimeException::new);

        when(userRepository.findById(id)).thenReturn(userOptional);

        UserResponse userResponse = userService.findById(id);
        assertThat(userResponse.getId()).isEqualTo(id);
        assertThat(userResponse.getUsername()).isEqualTo(user.getUsername());
        assertThat(userResponse.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findById_throws_UserNotFoundException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void deleteById_works_fine(long id) {
        doNothing().when(userRepository).deleteById(id);

        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(id);
    }
}