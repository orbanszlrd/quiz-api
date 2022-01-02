package com.orbanszlrd.quizapi.modules.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public Answer add(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer update(Long id, Answer answer) {
        answer.setId(id);
        return answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public List<Answer> findCorrectAnswersByQuestionId(Long questionId) {
        return answerRepository.findCorrectAnswersByQuestionId(questionId);
    }
}
