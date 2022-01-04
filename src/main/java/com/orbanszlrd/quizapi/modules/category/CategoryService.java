package com.orbanszlrd.quizapi.modules.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public long count() {
        return categoryRepository.count();
    }
}
