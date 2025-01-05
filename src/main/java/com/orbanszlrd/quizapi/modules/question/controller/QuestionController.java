package com.orbanszlrd.quizapi.modules.question.controller;

import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionRequest;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
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
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;

    @GetMapping("/questions")
    public String findAll(Model model, HttpServletRequest request) {
        List<QuestionResponse> questions = questionService.findAll();

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("questions", questions);
        return "question/questions";
    }

    @GetMapping("/quizzes/{parentId}/questions")
    public String questions(@PathVariable Long parentId, Model model, HttpServletRequest request) {
        List<QuestionResponse> questions = questionService.findByQuizId(parentId);

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("questions", questions);
        return "question/questions";
    }

    @GetMapping(value = {"/questions/create", "/quizzes/{parentId}/questions/create"})
    public String create(@PathVariable(required = false) Long parentId, Model model) {
        String randomText = RandomStringUtils.randomAlphanumeric(10);
        QuestionRequest question = new QuestionRequest(randomText, 30, (byte) 5, parentId);
        List<QuizResponse> quizzes = quizService.findAll();

        String action = (parentId == null) ? "/questions" : "/quizzes/" + parentId + "/questions";

        model.addAttribute("question", question);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("action", action);
        model.addAttribute("method", "POST");

        return "question/edit-question";
    }

    @GetMapping(value = {"/questions/{id}", "/quizzes/{parentId}/questions/{id}"})
    public String edit(@PathVariable Long id, @PathVariable(required = false) Long parentId, Model model) {
        QuestionResponse question = questionService.findById(id);
        List<QuizResponse> quizzes = quizService.findAll();

        String action = parentId == null ? "/questions/" + id : "/quizzes/" + parentId + "/questions/" + id;

        model.addAttribute("question", question);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", action);

        return "question/edit-question";
    }

    @PostMapping(value = {"/questions", "/quizzes/{parentId}/questions"})
    public String add(
            @PathVariable(required = false) Long parentId,
            @RequestParam("text") String text,
            @RequestParam("timeLimit") Integer timeLimit,
            @RequestParam("value") Byte value,
            @RequestParam("quizId") Long quizId
    ) {
        QuestionRequest questionRequest = new QuestionRequest(text, timeLimit, value, quizId);
        questionService.add(questionRequest);

        return "redirect:" + (parentId == null ? "/questions" : "/quizzes/" + parentId + "/questions");
    }

    @PutMapping(value = {"/questions/{id}", "/quizzes/{parentId}/questions/{id}"})
    public String update(
            @PathVariable Long id,
            @PathVariable(required = false) Long parentId,
            @RequestParam("text") String text,
            @RequestParam("timeLimit") Integer timeLimit,
            @RequestParam("value") Byte value,
            @RequestParam("quizId") Long quizId
    ) {
        QuestionRequest questionRequest = new QuestionRequest(text, timeLimit, value, quizId);
        questionService.update(id, questionRequest);

        return "redirect:" + (parentId == null ? "/questions" : "/quizzes/" + parentId + "/questions");
    }

    @DeleteMapping(value = {"/questions/{id}", "/quizzes/{parentId}/questions/{id}"})
    public String deleteById(@PathVariable Long id, @PathVariable(required = false) Long parentId) {
        questionService.deleteById(id);

        return "redirect:" + (parentId == null ? "/questions" : "/quizzes/" + parentId + "/questions");
    }
}
