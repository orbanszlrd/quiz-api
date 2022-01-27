package com.orbanszlrd.quizapi.modules.useranswer;

import com.orbanszlrd.quizapi.modules.answer.error.AnswerNotFoundException;
import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.answer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;
    private final AnswerRepository answerRepository;

    public List<UserAnswer> findAll() {
        return userAnswerRepository.findAll();
    }

    public List<UserAnswer> findByUserQuizId(Long userQuizId) {
        return userAnswerRepository.findByUserQuizId(userQuizId);
    }

    public UserAnswer findById(Long id) {
        return userAnswerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public UserAnswer update(Long userQuizId, Long questionId, Long answerId) {
        UserAnswer userAnswer = userAnswerRepository.findByUserQuizIdAndQuestionId(userQuizId, questionId).orElseThrow(RuntimeException::new);
        userAnswer.setAnswer(answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException(answerId)));
        userAnswerRepository.save(userAnswer);

        return userAnswer;
    }

    public UserAnswer add(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    public UserAnswer update(Long id, UserAnswer userAnswer) {
        userAnswer.setId(id);
        return userAnswerRepository.save(userAnswer);
    }

    public void deleteById(Long id) {
        userAnswerRepository.deleteById(id);
    }

    public long count() {
        return userAnswerRepository.count();
    }
}
