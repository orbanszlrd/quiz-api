package com.orbanszlrd.quizapi.modules.question.controller;

import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionRequest;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;

    @GetMapping("")
    public String findAll(Model model) {
        List<QuestionResponse> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question/questions";
    }

    @GetMapping("/create")
    public String create(Model model) {
        String randomText = RandomStringUtils.randomAlphanumeric(10);
        QuestionRequest question = new QuestionRequest(randomText, 30, (byte) 5, 1L);
        List<QuizResponse> quizzes = quizService.findAll();

        model.addAttribute("question", question);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("action", "/questions");
        model.addAttribute("method", "POST");

        return "question/edit-question";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        QuestionResponse question = questionService.findById(id);
        List<QuizResponse> quizzes = quizService.findAll();

        model.addAttribute("question", question);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/questions/" + id);

        return "question/edit-question";
    }

    @PostMapping("")
    public String add(@RequestParam("text") String text, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("value") Byte value, @RequestParam("quizId") Long quizId) {
        QuestionRequest questionRequest = new QuestionRequest(text, timeLimit, value, quizId);
        questionService.add(questionRequest);

        return "redirect:/questions";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("text") String text, @RequestParam("timeLimit") Integer timeLimit, @RequestParam("value") Byte value, @RequestParam("quizId") Long quizId) {
        QuestionRequest questionRequest = new QuestionRequest(text, timeLimit, value, quizId);
        questionService.update(id, questionRequest);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        questionService.deleteById(id);

        return "redirect:/questions";
    }
}
