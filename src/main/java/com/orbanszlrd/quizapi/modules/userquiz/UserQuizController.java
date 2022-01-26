package com.orbanszlrd.quizapi.modules.userquiz;

import com.orbanszlrd.quizapi.modules.answer.model.dto.AnswerResponse;
import com.orbanszlrd.quizapi.modules.answer.service.AnswerService;
import com.orbanszlrd.quizapi.modules.question.model.dto.QuestionResponse;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
import com.orbanszlrd.quizapi.modules.quiz.model.dto.QuizResponse;
import com.orbanszlrd.quizapi.modules.quiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/quiz")
public class UserQuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public UserQuizController(QuizService quizService, QuestionService questionService, AnswerService answerService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public String quiz(@PathVariable Long id, Model model) {
        QuizResponse quiz = quizService.findById(id);
        List<QuestionResponse> questions = questionService.findByQuizId(id);
        List<AnswerResponse> answers = answerService.findByQuestionId(questions.get(0).getId());

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);

        return "userquiz/index";
    }

}
