package com.orbanszlrd.quizapi.modules.answer.repository;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Override
    @NonNull
    @Query("SELECT a FROM Answer a JOIN FETCH a.question qu JOIN FETCH qu.quiz qz JOIN FETCH qz.category")
    List<Answer> findAll();

    @Query("SELECT a FROM Answer a JOIN a.question qu WHERE qu.quiz.id = :quizId")
    List<Answer> findByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId")
    List<Answer> findByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId AND a.correct=true")
    List<Answer> findCorrectAnswersByQuestionId(@Param("questionId") Long questionId);
}
