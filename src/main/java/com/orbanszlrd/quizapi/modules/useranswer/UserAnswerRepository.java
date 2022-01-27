package com.orbanszlrd.quizapi.modules.useranswer;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    @Query("SELECT ua FROM UserAnswer ua WHERE ua.userQuiz.id = :userQuizId")
    List<UserAnswer> findByUserQuizId(@Param("userQuizId") Long userQuizId);

    @Query("SELECT ua FROM UserAnswer ua WHERE ua.userQuiz.id = :userQuizId AND ua.question.id = :questionId")
    Optional<UserAnswer> findByUserQuizIdAndQuestionId(@Param("userQuizId") Long userQuizId, @Param("questionId") Long questionId);
}
