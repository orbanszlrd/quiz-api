package com.orbanszlrd.quizapi.modules.answer.controller;

import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerRequest;
import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerResponse;
import com.orbanszlrd.quizapi.modules.answer.service.AnswerService;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @GetMapping("")
    public String findAll(Model model) {
        List<AnswerResponse> answers = answerService.findAll();
        model.addAttribute("answers", answers);
        return "answer/answers";
    }

    @GetMapping("/create")
    public String create(Model model) {
        String randomText = RandomStringUtils.randomAlphanumeric(10);
        AnswerRequest answer = new AnswerRequest(randomText, false , 1L);
        List<QuestionResponse> questions = questionService.findAll();

        model.addAttribute("answer", answer);
        model.addAttribute("questions", questions);
        model.addAttribute("action", "/answers");
        model.addAttribute("method", "POST");

        return "answer/edit-answer";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        AnswerResponse answer = answerService.findById(id);
        List<QuestionResponse> questions = questionService.findAll();

        model.addAttribute("answer", answer);
        model.addAttribute("questions", questions);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/answers/" + id);

        return "answer/edit-answer";
    }

    @PostMapping("")
    public String add(@RequestParam("text") String text, @RequestParam(value = "correct", required = false) boolean correct, @RequestParam("questionId") Long questionId) {
        AnswerRequest answerRequest = new AnswerRequest(text, correct, questionId);
        answerService.add(answerRequest);

        return "redirect:/answers";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("text") String text, @RequestParam(value = "correct", required = false) boolean correct, @RequestParam("questionId") Long questionId) {
        AnswerRequest answerRequest = new AnswerRequest(text, correct, questionId);
        answerService.update(id, answerRequest);

        return "redirect:/answers";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        answerService.deleteById(id);

        return "redirect:/answers";
    }
}
