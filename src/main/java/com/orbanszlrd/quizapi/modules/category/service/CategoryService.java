package com.orbanszlrd.quizapi.modules.category.service;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryResponse> findAll() {
        Type type = new TypeToken<List<CategoryResponse>>() {
        }.getType();

        List<Category> categories = categoryRepository.findAll();

        return modelMapper.map(categories, type);
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        return modelMapper.map(category, CategoryResponse.class);
    }

    public CategoryResponse add(Category category) {
        return modelMapper.map(categoryRepository.save(category), CategoryResponse.class);
    }

    public CategoryResponse update(Long id, Category category) {
        category.setId(id);
        return modelMapper.map(categoryRepository.save(category), CategoryResponse.class);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public long count() {
        return categoryRepository.count();
    }
}
