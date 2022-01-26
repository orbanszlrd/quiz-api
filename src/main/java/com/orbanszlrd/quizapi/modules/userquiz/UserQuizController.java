package com.orbanszlrd.quizapi.modules.userquiz;

import com.orbanszlrd.quizapi.modules.answer.service.AnswerService;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.repository.QuestionRepository;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/quiz")
public class UserQuizController {
    private final QuizService quizService;
    private final AnswerService answerService;

    private final QuestionRepository questionRepository;

    @GetMapping("/{id}")
    public String quiz(@PathVariable Long id, Model model) {
        QuizResponse quiz = quizService.findById(id);
        List<Question> questions = questionRepository.findAnswersByQuizId(id);
        Collections.shuffle(questions);

        questions = questions.size() > 20 ? questions.subList(0, 20) : questions;

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("action", "/user/quiz/" + id);

        return "userquiz/index";
    }

    @PostMapping("/{id}")
    public String quiz(@PathVariable Long id, @RequestParam Map<String, String> requestParams, Model model) {
        QuizResponse quiz = quizService.findById(id);

        int result = 0;

        for (String answerId : requestParams.values()) {
            if (answerService.findById(Long.valueOf(answerId)).isCorrect()) {
                result++;
            }
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("result", result);

        return "userquiz/result";
    }
}
