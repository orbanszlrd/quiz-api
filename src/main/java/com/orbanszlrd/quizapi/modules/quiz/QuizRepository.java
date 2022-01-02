package com.orbanszlrd.quizapi.modules.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q FROM Quiz q WHERE q.category.id = :categoryId")
    List<Quiz> findByCategoryId(@Param("categoryId") Long categoryId);
}
