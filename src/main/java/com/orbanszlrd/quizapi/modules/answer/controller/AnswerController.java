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
@RequestMapping("")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @GetMapping("/answers")
    public String findAll(Model model) {
        List<AnswerResponse> answers = answerService.findAll();
        model.addAttribute("answers", answers);
        return "answer/answers";
    }

    @GetMapping("/questions/{parentId}/answers")
    public String findByQuestionId(@PathVariable Long parentId, Model model) {
        List<AnswerResponse> answers = answerService.findByQuestionId(parentId);
        model.addAttribute("answers", answers);
        return "answer/answers";
    }

    @GetMapping(value = {"/answers/create", "/questions/{parentId}/answers/create"})
    public String create(@PathVariable(required = false) Long parentId, Model model) {
        String randomText = RandomStringUtils.randomAlphanumeric(10);
        AnswerRequest answer = new AnswerRequest(randomText, false , parentId);
        List<QuestionResponse> questions = questionService.findAll();

        String action = (parentId == null) ? "/answers" : "/questions/" + parentId + "/answers";

        model.addAttribute("answer", answer);
        model.addAttribute("questions", questions);
        model.addAttribute("action", action);
        model.addAttribute("method", "POST");

        return "answer/edit-answer";
    }

    @GetMapping(value = {"/answers/{id}", "/questions/{parentId}/answers/{id}"})
    public String edit(@PathVariable Long id, @PathVariable(required = false) Long parentId, Model model) {
        AnswerResponse answer = answerService.findById(id);
        List<QuestionResponse> questions = questionService.findAll();

        String action = (parentId == null) ? "/answers/" + id : "/questions/" + parentId + "/answers/" + id;

        model.addAttribute("answer", answer);
        model.addAttribute("questions", questions);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", action);

        return "answer/edit-answer";
    }

    @PostMapping(value = {"/answers", "/questions/{parentId}/answers"})
    public String add(
            @PathVariable(required = false) Long parentId,
            @RequestParam("text") String text,
            @RequestParam(value = "correct", required = false) boolean correct,
            @RequestParam("questionId") Long questionId
    ) {
        AnswerRequest answerRequest = new AnswerRequest(text, correct, questionId);
        answerService.add(answerRequest);

        return "redirect:" + (parentId == null ? "/answers" : "/questions/" + parentId + "/answers");
    }

    @PutMapping(value = {"/answers/{id}", "/questions/{parentId}/answers/{id}"})
    public String update(
            @PathVariable Long id,
            @PathVariable(required = false) Long parentId,
            @RequestParam("text") String text,
            @RequestParam(value = "correct", required = false) boolean correct,
            @RequestParam("questionId") Long questionId
    ) {
        AnswerRequest answerRequest = new AnswerRequest(text, correct, questionId);
        answerService.update(id, answerRequest);

        return "redirect:" + (parentId == null ? "/answers" : "/questions/" + parentId + "/answers");
    }

    @DeleteMapping(value = {"/answers/{id}", "/questions/{parentId}/answers/{id}"})
    public String deleteById(@PathVariable Long id, @PathVariable(required = false) Long parentId) {
        answerService.deleteById(id);

        return "redirect:" + (parentId == null ? "/answers" : "/questions/" + parentId + "/answers");
    }
}
