package com.orbanszlrd.quizapi.modules.category.controller;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping("")
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("")
    public CategoryResponse add(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
