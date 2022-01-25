package com.orbanszlrd.quizapi.modules.category.controller;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryRequest;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizRequest;
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

    @GetMapping("/{id}/quizzes")
    public String quizzes(@PathVariable Long id, Model model) {
        List<QuizResponse> quizzes = quizService.findByCategoryId(id);
        model.addAttribute("quizzes", quizzes);
        return "quiz/quizzes";
    }

    @GetMapping("/{id}/quizzes/create")
    public String createQuiz(@PathVariable Long id, Model model) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        QuizRequest quiz = new QuizRequest(randomName, 30, id);

        List<CategoryResponse> categories = categoryService.findAll();

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "/categories/" + id + "/quizzes");
        model.addAttribute("method", "POST");

        return "quiz/edit-quiz";
    }

    @PostMapping("/{id}/quizzes")
    public String addQuiz(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("categoryId") Long categoryId) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.add(quizRequest);

        return "redirect:/categories/" + id + "/quizzes";
    }

    @GetMapping("/{id}/quizzes/{quizId}")
    public String editQuiz(@PathVariable Long id, @PathVariable Long quizId, Model model) {
        QuizResponse quiz = quizService.findById(quizId);
        List<CategoryResponse> categories = categoryService.findAll();

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/categories/" + id + "/quizzes/" + quizId);

        return "quiz/edit-quiz";
    }

    @PutMapping("/{id}/quizzes/{quizId}")
    public String update(@PathVariable Long id, @PathVariable Long quizId, @RequestParam("name") String name, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("categoryId") Long categoryId) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.update(quizId, quizRequest);

        return "redirect:/categories/" + id + "/quizzes";
    }

    @DeleteMapping("/{id}/quizzes/{quizId}")
    public String deleteQuizById(@PathVariable Long id, @PathVariable Long quizId) {
        quizService.deleteById(quizId);

        return "redirect:/categories/" + id + "/quizzes";
    }
}
