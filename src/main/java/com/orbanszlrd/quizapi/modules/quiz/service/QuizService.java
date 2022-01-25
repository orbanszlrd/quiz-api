package com.orbanszlrd.quizapi.modules.quiz.service;

import com.orbanszlrd.quizapi.modules.category.error.CategoryNotFoundException;
import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import com.orbanszlrd.quizapi.modules.quiz.error.QuizNotFoundException;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizRequest;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<QuizResponse> findAll() {
        Type type = new TypeToken<List<QuizResponse>>() {
        }.getType();

        List<Quiz> categories = quizRepository.findAll();

        return modelMapper.map(categories, type);
    }

    public List<QuizResponse> findByCategoryId(Long categoryId) {
        Type type = new TypeToken<List<QuizResponse>>() {
        }.getType();

        List<Quiz> categories = quizRepository.findByCategoryId(categoryId);

        return modelMapper.map(categories, type);
    }

    public QuizResponse findById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        return modelMapper.map(quiz, QuizResponse.class);
    }

    public QuizResponse add(QuizRequest quizRequest) {
        Category category = categoryRepository.findById(quizRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(quizRequest.getCategoryId()));
        Quiz quiz = new Quiz(quizRequest.getName(), quizRequest.getTimeLimit(), category);
        return modelMapper.map(quizRepository.save(quiz), QuizResponse.class);
    }

    public QuizResponse update(Long id, QuizRequest quizRequest) {
        Category category = categoryRepository.findById(quizRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(quizRequest.getCategoryId()));
        Quiz quiz = new Quiz(id, quizRequest.getName(), quizRequest.getTimeLimit(), category);
        return modelMapper.map(quizRepository.save(quiz), QuizResponse.class);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }

    public long count() {
        return quizRepository.count();
    }
}
