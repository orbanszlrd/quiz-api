package com.orbanszlrd.quizapi.modules.answer.repository;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId")
    List<Answer> findByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId AND a.correct=true")
    List<Answer> findCorrectAnswersByQuestionId(@Param("questionId") Long questionId);
}
