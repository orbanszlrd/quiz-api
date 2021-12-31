package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;

    private final List<User> users = List.of(
            new User(1L, "tom", "tom@email.com", passwordEncoder.encode("tom"), true, Role.ADMIN),
            new User(2L, "jim", "jim@email.com", passwordEncoder.encode("jim"), false, Role.USER),
            new User(3L, "ben", "ben@email.com", passwordEncoder.encode("ben"), true, Role.USER)
    );

    @BeforeEach
    void setUp() {
        userService = new UserService(passwordEncoder, modelMapper, userRepository);
    }

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
        List<GetUser> getUsers = userService.findAll();
        assertThat(getUsers.size()).isEqualTo(users.size());
    }

    @Test
    void add_works_fine() {
        AddUser addUser = new AddUser("joe", "joe@email.com", passwordEncoder.encode("joe"), Role.ADMIN);
        GetUser getUser = userService.add(addUser);
        assertThat(getUser.getUsername()).isEqualTo(addUser.getUsername());
        assertThat(getUser.getEmail()).isEqualTo(addUser.getEmail());
        assertThat(getUser.getRole()).isEqualTo(addUser.getRole());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void update_works_fine(long id) {
        when(userRepository.findById(id)).thenReturn(users.stream().filter(user -> user.getId() == id).findFirst());

        UpdateUser updateUser = new UpdateUser("bill", "bill@email.com", "bill", false, Role.ADMIN);
        GetUser getUser = userService.update(id, updateUser);

        assertThat(getUser.getUsername()).isEqualTo(updateUser.getUsername());
        assertThat(getUser.getEmail()).isEqualTo(updateUser.getEmail());
        assertThat(getUser.getRole()).isEqualTo(updateUser.getRole());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findById_works_fine(long id) {
        Optional<User> userOptional = users.stream().filter(user -> user.getId() == id).findFirst();
        User user = userOptional.orElseThrow(RuntimeException::new);

        when(userRepository.findById(id)).thenReturn(userOptional);

        GetUser getUser = userService.findById(id);
        assertThat(getUser.getId()).isEqualTo(id);
        assertThat(getUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(getUser.getEmail()).isEqualTo(user.getEmail());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void deleteById_works_fine(long id) {
        doNothing().when(userRepository).deleteById(id);

        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(id);
    }
}