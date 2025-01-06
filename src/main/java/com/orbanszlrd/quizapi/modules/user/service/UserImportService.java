package com.orbanszlrd.quizapi.modules.user.service;

import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.User;
import com.orbanszlrd.quizapi.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImportService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUsers() {
        userRepository.save(new User("owner", "owner@email.com", passwordEncoder.encode("owner"), true, Role.OWNER));
        userRepository.save(new User("admin", "admin@email.com", passwordEncoder.encode("admin"), true, Role.ADMIN));
        userRepository.save(new User("user", "user@email.com", passwordEncoder.encode("user"), true, Role.USER));
    }
}
