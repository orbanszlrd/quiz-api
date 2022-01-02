package com.orbanszlrd.quizapi.modules.question;

import com.orbanszlrd.quizapi.modules.quiz.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question add(Question question) {
        return questionRepository.save(question);
    }

    public Question update(Long id, Question question) {
        question.setId(id);
        return questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public List<Question> findByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }
}
