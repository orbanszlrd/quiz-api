package com.orbanszlrd.quizapi.modules.userquiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuizService {
    private final UserQuizRepository userQuizRepository;

    public List<UserQuiz> findAll() {
        return userQuizRepository.findAll();
    }

    public UserQuiz add(UserQuiz userQuiz) {
        return userQuizRepository.save(userQuiz);
    }

    public UserQuiz update(Long id, UserQuiz userQuiz) {
        userQuiz.setId(id);
        return userQuizRepository.save(userQuiz);
    }

    public UserQuiz findById(Long id) {
        return userQuizRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long id) {
        userQuizRepository.deleteById(id);
    }

    public long count() {
        return userQuizRepository.count();
    }

}
