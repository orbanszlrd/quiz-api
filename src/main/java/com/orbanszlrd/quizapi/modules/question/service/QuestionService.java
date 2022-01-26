package com.orbanszlrd.quizapi.modules.question.service;

import com.orbanszlrd.quizapi.modules.question.error.QuestionNotFoundException;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionRequest;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import com.orbanszlrd.quizapi.modules.question.repository.QuestionRepository;
import com.orbanszlrd.quizapi.modules.quiz.error.QuizNotFoundException;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public List<QuestionResponse> findAll() {
        Type type = new TypeToken<List<QuestionResponse>>() {
        }.getType();

        List<Question> questions = questionRepository.findAll();

        return modelMapper.map(questions, type);
    }

    public List<QuestionResponse> findByQuizId(Long quizId) {
        Type type = new TypeToken<List<QuestionResponse>>() {
        }.getType();

        List<Question> questions = questionRepository.findByQuizId(quizId);

        return modelMapper.map(questions, type);
    }

    public QuestionResponse findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return modelMapper.map(question, QuestionResponse.class);
    }

    public QuestionResponse add(QuestionRequest questionRequest) {
        Quiz quiz = quizRepository.findById(questionRequest.getQuizId()).orElseThrow(() -> new QuizNotFoundException(questionRequest.getQuizId()));
        Question question = new Question(questionRequest.getText(), questionRequest.getTimeLimit(), questionRequest.getValue(), quiz);
        return modelMapper.map(questionRepository.save(question), QuestionResponse.class);
    }

    public QuestionResponse update(Long id, QuestionRequest questionRequest) {
        Quiz quiz = quizRepository.findById(questionRequest.getQuizId()).orElseThrow(() -> new QuizNotFoundException(questionRequest.getQuizId()));
        Question question = new Question(id, questionRequest.getText(), questionRequest.getTimeLimit(), questionRequest.getValue(), quiz);
        return modelMapper.map(questionRepository.save(question), QuestionResponse.class);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public long count() {
        return questionRepository.count();
    }
}
