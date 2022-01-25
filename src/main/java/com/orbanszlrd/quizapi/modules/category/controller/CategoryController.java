package com.orbanszlrd.quizapi.modules.category.controller;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryRequest;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final QuizService quizService;

    @GetMapping("")
    public String findAll(Model model) {
        List<CategoryResponse> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/categories";
    }

    @GetMapping("/create")
    public String create(Model model) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        Category category = new Category(randomName);

        model.addAttribute("category", category);
        model.addAttribute("action", "/categories");
        model.addAttribute("method", "POST");

        return "category/edit-category";
    }

    @GetMapping("/{id}/quizzes")
    public String quizzes(@PathVariable Long id, Model model) {
        List<QuizResponse> quizzes = quizService.findByCategoryId(id);
        model.addAttribute("quizzes", quizzes);
        return "quiz/quizzes";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        CategoryResponse category = categoryService.findById(id);

        model.addAttribute("category", category);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/categories/" + id);

        return "category/edit-category";
    }

    @PostMapping("")
    public String add(@RequestParam("name") String name) {
        CategoryRequest categoryRequest = new CategoryRequest(name);
        categoryService.add(categoryRequest);

        return "redirect:/categories";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("name") String name) {
        CategoryRequest categoryRequest = new CategoryRequest(name);
        categoryService.update(id, categoryRequest);

        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

        return "redirect:/categories";
    }
}
