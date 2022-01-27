package com.orbanszlrd.quizapi.modules.userquiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    @Query("SELECT uq FROM UserQuiz uq WHERE uq.user.id = :userId")
    List<UserQuiz> findByUserId(@Param("userId") Long userId);
}
