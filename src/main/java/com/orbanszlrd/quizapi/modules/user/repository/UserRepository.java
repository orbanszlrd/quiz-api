package com.orbanszlrd.quizapi.modules.user.repository;

import com.orbanszlrd.quizapi.modules.user.model.Role;
import com.orbanszlrd.quizapi.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    Optional<User> findByRole(Role role);
}
