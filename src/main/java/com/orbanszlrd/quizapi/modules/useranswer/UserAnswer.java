package com.orbanszlrd.quizapi.modules.useranswer;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.userquiz.UserQuiz;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserQuiz userQuiz;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER)
    private Answer answer;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updatedAt;

    public UserAnswer(UserQuiz userQuiz, Question question, Answer answer) {
        this.userQuiz = userQuiz;
        this.question = question;
        this.answer = answer;
    }

    public UserAnswer(Long id, UserQuiz userQuiz, Question question, Answer answer) {
        this.id = id;
        this.userQuiz = userQuiz;
        this.question = question;
        this.answer = answer;
    }
}
