package com.orbanszlrd.quizapi.modules.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q FROM quiz q WHERE q.category.id = ?1")
    List<Quiz> findByCategoryId(Long categoryId);
}
