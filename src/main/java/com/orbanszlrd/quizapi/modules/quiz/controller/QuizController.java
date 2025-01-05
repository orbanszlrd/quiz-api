package com.orbanszlrd.quizapi.modules.quiz.controller;

import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizRequest;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final CategoryService categoryService;

    @GetMapping("/quizzes")
    public String findAll(Model model, HttpServletRequest request) {
        List<QuizResponse> quizzes = quizService.findAll();

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("quizzes", quizzes);
        return "quiz/quizzes";
    }

    @GetMapping("/categories/{parentId}/quizzes")
    public String findByCategoryId(@PathVariable Long parentId, Model model, HttpServletRequest request) {
        List<QuizResponse> quizzes = quizService.findByCategoryId(parentId);

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("quizzes", quizzes);
        return "quiz/quizzes";
    }

    @GetMapping(value = {"/quizzes/create", "/categories/{parentId}/quizzes/create"})
    public String create(@PathVariable(required = false) Long parentId, Model model) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        QuizRequest quiz = new QuizRequest(randomName, 30, parentId);
        List<CategoryResponse> categories = categoryService.findAll();

        String action = parentId == null ? "/quizzes" : "/categories/" + parentId + "/quizzes";

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("action", action);
        model.addAttribute("method", "POST");

        return "quiz/edit-quiz";
    }

    @GetMapping(value = {"/quizzes/{id}", "/categories/{parentId}/quizzes/{id}"})
    public String edit(@PathVariable Long id, @PathVariable(required = false) Long parentId, Model model) {
        QuizResponse quiz = quizService.findById(id);
        List<CategoryResponse> categories = categoryService.findAll();

        String action = parentId == null ? "/quizzes/" + id : "/categories/" + parentId + "/quizzes/" + id;

        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", categories);
        model.addAttribute("action", action);
        model.addAttribute("method", "PUT");

        return "quiz/edit-quiz";
    }

    @PostMapping(value = {"/quizzes", "/categories/{parentId}/quizzes"})
    public String add(
            @PathVariable(required = false) Long parentId,
            @RequestParam("name") String name,
            @RequestParam("timeLimit") Integer timeLimit,
            @RequestParam("categoryId") Long categoryId
    ) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.add(quizRequest);

        return "redirect:" + (parentId == null ? "/quizzes" : "/categories/" + parentId + "/quizzes");
    }

    @PutMapping(value = {"/quizzes/{id}", "/categories/{parentId}/quizzes/{id}"})
    public String update(
            @PathVariable Long id,
            @PathVariable(required = false) Long parentId,
            @RequestParam("name") String name,
            @RequestParam("timeLimit") Integer timeLimit,
            @RequestParam("categoryId") Long categoryId
    ) {
        QuizRequest quizRequest = new QuizRequest(name, timeLimit, categoryId);
        quizService.update(id, quizRequest);

        return "redirect:" + (parentId == null ? "/quizzes" : "/categories/" + parentId + "/quizzes");
    }

    @DeleteMapping(value = {"/quizzes/{id}", "/categories/{parentId}/quizzes/{id}"})
    public String deleteById(@PathVariable Long id, @PathVariable(required = false) Long parentId) {
        quizService.deleteById(id);

        return "redirect:" + (parentId == null ? "/quizzes" : "/categories/" + parentId + "/quizzes");
    }
}
