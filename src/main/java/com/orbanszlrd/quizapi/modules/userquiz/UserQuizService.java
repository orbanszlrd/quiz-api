package com.orbanszlrd.quizapi.modules.userquiz;

import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.quiz.error.QuizNotFoundException;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import com.orbanszlrd.quizapi.modules.user.model.User;
import com.orbanszlrd.quizapi.modules.user.repository.UserRepository;
import com.orbanszlrd.quizapi.modules.useranswer.UserAnswer;
import com.orbanszlrd.quizapi.modules.useranswer.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuizService {
    private final UserQuizRepository userQuizRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserAnswerRepository userAnswerRepository;

    public List<UserQuiz> findAll() {
        return userQuizRepository.findAll();
    }

    public UserQuiz findById(Long id) {
        return userQuizRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public UserQuiz add(UserQuiz userQuiz) {
        return userQuizRepository.save(userQuiz);
    }

    public UserQuiz update(Long id, UserQuiz userQuiz) {
        userQuiz.setId(id);
        return userQuizRepository.save(userQuiz);
    }

    public void deleteById(Long id) {
        userQuizRepository.deleteById(id);
    }

    public long count() {
        return userQuizRepository.count();
    }

    public UserQuiz start(Principal principal, Long quizId, List<Question> questions) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        UserQuiz userQuiz = userQuizRepository.save(new UserQuiz(user, quiz));

        for (Question question : questions) {
            userAnswerRepository.save(new UserAnswer(userQuiz, question, null));
        }

        return userQuizRepository.save(userQuiz);
    }

    public List<UserQuiz> findByUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        return userQuizRepository.findByUserId(user.getId());
    }
}
