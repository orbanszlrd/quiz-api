package com.orbanszlrd.quizapi.modules.question;

import com.orbanszlrd.quizapi.modules.answer.Answer;
import com.orbanszlrd.quizapi.modules.quiz.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer timeLimit;

    private Byte value;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Question(String text, Integer timeLimit, Byte value, Quiz quiz) {
        this.text = text;
        this.timeLimit = timeLimit;
        this.value = value;
        this.quiz = quiz;
    }

    public Question(Long id, String text, Integer timeLimit, Byte value, Quiz quiz) {
        this.id = id;
        this.text = text;
        this.timeLimit = timeLimit;
        this.value = value;
        this.quiz = quiz;
    }
}
