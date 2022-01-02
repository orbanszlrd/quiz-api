package com.orbanszlrd.quizapi.modules.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM answer a WHERE a.question.id = ?1")
    List<Answer> findByQuestionId(Long questionId);

    @Query("SELECT a FROM answer a WHERE a.question.id = ?1 AND a.correct=true")
    List<Answer> findCorrectAnswersByQuestionId(Long questionId);
}
