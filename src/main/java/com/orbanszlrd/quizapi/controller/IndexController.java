package com.orbanszlrd.quizapi.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class IndexController {
    @GetMapping("")
    public String index(Model model) {
        List<QuizModule> modules = List.of(
                new QuizModule("Categories", ""),
                new QuizModule("Quizzes", ""),
                new QuizModule("Questions", ""),
                new QuizModule("Answers", ""),
                new QuizModule("Users", "")
        );

        model.addAttribute("modules", modules);

        return "index";
    }

    @Data
    @AllArgsConstructor
    private static class QuizModule {
        private String title;
        private String description;
    }
}
