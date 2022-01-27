package com.orbanszlrd.quizapi.modules.userquiz;

import com.orbanszlrd.quizapi.modules.answer.service.AnswerService;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.service.QuestionService;
import com.orbanszlrd.quizapi.modules.useranswer.UserAnswer;
import com.orbanszlrd.quizapi.modules.useranswer.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/quizzes")
public class UserQuizController {
    private final UserQuizService userQuizService;
    private final UserAnswerService userAnswerService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("")
    public String findByUser(Model model, Principal principal) {
        List<UserQuiz> userQuizzes = userQuizService.findByUser(principal);

        model.addAttribute("userQuizzes", userQuizzes);

        return "user_quiz/list";
    }

    @PostMapping("")
    public String create(@RequestParam Long quizId, Principal principal) {
        List<Question> questions = questionService.findRandomByQuizId(quizId);

        UserQuiz userQuiz = userQuizService.start(principal, quizId, questions);

        return "redirect:/user/quizzes/" + userQuiz.getId();
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        UserQuiz userQuiz = userQuizService.findById(id);

        if (Timestamp.valueOf(LocalDateTime.now()).getTime() - userQuiz.getCreatedAt().getTime() > userQuiz.getQuiz().getTimeLimit() * 60 * 1000) {
            userQuiz.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userQuizService.update(id, userQuiz);
        }

        if (userQuiz.getUpdatedAt() != null) {
            List<UserAnswer> userAnswers = userAnswerService.findByUserQuizId(id);

            int count = userAnswers.size();
            long correct = userAnswers.stream().filter(userAnswer -> userAnswer.getAnswer() != null && userAnswer.getAnswer().isCorrect()).count();
            long unanswered = userAnswers.stream().filter(userAnswer -> userAnswer.getAnswer() == null).count();

            model.addAttribute("quizName", userQuiz.getQuiz().getName());
            model.addAttribute("count", count);
            model.addAttribute("correct", correct);
            model.addAttribute("unanswered", unanswered);

            return "user_quiz/result";
        } else {
            List<Question> questions = questionService.findByUserQuizId(id);

            model.addAttribute("quizName", userQuiz.getQuiz().getName());
            model.addAttribute("questions", questions);

            return "user_quiz/index";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam Map<String, String> requestParams, Model model) {
        UserQuiz userQuiz = userQuizService.findById(id);
        userQuiz.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userQuizService.update(id, userQuiz);

        int result = 0;

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            Long questionId = Long.valueOf(entry.getKey().substring(2));
            Long answerId = Long.valueOf(entry.getValue());
            userAnswerService.update(id, questionId, answerId);

            if (answerService.findById(answerId).isCorrect()) {
                result++;
            }
        }

        return "redirect:/user/quizzes/" + userQuiz.getId();
    }
}
