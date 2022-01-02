package com.orbanszlrd.quizapi.modules.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM question q WHERE q.quiz.id = ?1")
    List<Question> findByQuizId(Long quizId);
}
