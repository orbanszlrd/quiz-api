package com.orbanszlrd.quizapi.modules.question.repository;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT qu FROM Question qu JOIN FETCH qu.quiz qz JOIN FETCH qz.category")
    List<Question> findAll();

    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId")
    List<Question> findByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT DISTINCT q FROM Answer a JOIN a.question q WHERE q.quiz.id = :quizId")
    List<Question> findAnswersByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT DISTINCT ua.question FROM UserAnswer ua JOIN ua.question q JOIN q.answers WHERE ua.userQuiz.id = :userQuizId")
    List<Question> findByUserQuizId(Long userQuizId);
}
