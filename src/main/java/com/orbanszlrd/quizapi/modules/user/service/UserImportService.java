package com.orbanszlrd.quizapi.modules.user.service;

import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.User;
import com.orbanszlrd.quizapi.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImportService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner userImporter() {
        return args -> {
            userRepository.save(new User(1L, "owner", "owner@email.com", passwordEncoder.encode("owner"), true, Role.OWNER));
            userRepository.save(new User(2L, "admin", "admin@email.com", passwordEncoder.encode("admin"), true, Role.ADMIN));
            userRepository.save(new User(3L, "user", "user@email.com", passwordEncoder.encode("user"), true, Role.USER));
        };
    }
}
