package com.orbanszlrd.quizapi.modules.user.repository;

import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("Testing the UserRepository")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    private final List<User> users = List.of(
            new User(1L, "owner", "owner@email.com", passwordEncoder.encode("owner"), true, Role.OWNER),
            new User(2L, "admin", "admin@email.com", passwordEncoder.encode("admin"), false, Role.ADMIN),
            new User(3L, "user", "user@email.com", passwordEncoder.encode("user"), true, Role.USER)
    );

    @BeforeEach
    void setUp() {
        userRepository.saveAll(users);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"owner", "admin", "user"})
    @DisplayName("Find user by username")
    void findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get().getUsername()).isEqualTo(username);
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    @DisplayName("Find users by their role")
    void findByRole(Role role) {
        Optional<User> userOptional = userRepository.findByRole(role);
        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get().getRole()).isEqualTo(role);
    }
}