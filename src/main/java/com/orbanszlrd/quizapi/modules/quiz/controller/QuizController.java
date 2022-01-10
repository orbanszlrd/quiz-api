package com.orbanszlrd.quizapi.modules.quiz.controller;

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

import java.util.List;

@Controller
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String findAll(Model model) {
        List<QuizResponse> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        return "quiz/quizzes";
    }

    @GetMapping("/create")
    public String create(Model model) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        QuizRequest quiz = new QuizRequest(randomName, 30, 1L);
        List<CategoryResponse> categories = categoryService.findAll();

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "/quizzes");
        model.addAttribute("method", "POST");

        return "quiz/edit-quiz";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        QuizResponse quiz = quizService.findById(id);
        List<CategoryResponse> categories = categoryService.findAll();

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/quizzes/" + id);

        return "quiz/edit-quiz";
    }

    @PostMapping("")
    public String add(@RequestParam("name") String name, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("categoryId") Long categoryId) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.add(quizRequest);

        return "redirect:/quizzes";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("categoryId") Long categoryId) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.update(id, quizRequest);

        return "redirect:/quizzes";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        quizService.deleteById(id);

        return "redirect:/quizzes";
    }
}
