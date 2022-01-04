package com.orbanszlrd.quizapi.modules.useranswer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    public List<UserAnswer> findAll() {
        return userAnswerRepository.findAll();
    }

    public UserAnswer findById(Long id) {
        return userAnswerRepository.findById(id).orElseThrow(RuntimeException::new);
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
