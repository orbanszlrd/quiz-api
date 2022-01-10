package com.orbanszlrd.quizapi.modules.answer.service;

import com.orbanszlrd.quizapi.modules.answer.error.AnswerNotFoundException;
import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerRequest;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerResponse;
import com.orbanszlrd.quizapi.modules.answer.repository.AnswerRepository;
import com.orbanszlrd.quizapi.modules.question.error.QuestionNotFoundException;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public List<AnswerResponse> findAll() {
        Type type = new TypeToken<List<AnswerResponse>>() {
        }.getType();

        List<Answer> questionzes = answerRepository.findAll();

        return modelMapper.map(questionzes, type);
    }

    public AnswerResponse findById(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
        return modelMapper.map(answer, AnswerResponse.class);
    }

    public AnswerResponse add(AnswerRequest answerRequest) {
        Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(() -> new QuestionNotFoundException(answerRequest.getQuestionId()));
        Answer answer = new Answer(answerRequest.getText(), answerRequest.isCorrect(), question);
        return modelMapper.map(answerRepository.save(answer), AnswerResponse.class);
    }

    public AnswerResponse update(Long id, AnswerRequest answerRequest) {
        Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(() -> new QuestionNotFoundException(answerRequest.getQuestionId()));
        Answer answer = new Answer(id, answerRequest.getText(), answerRequest.isCorrect(), question);
        return modelMapper.map(answerRepository.save(answer), AnswerResponse.class);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public long count() {
        return answerRepository.count();
    }
}
