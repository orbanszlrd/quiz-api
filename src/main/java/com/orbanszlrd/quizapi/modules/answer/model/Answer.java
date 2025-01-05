package com.orbanszlrd.quizapi.modules.answer.model;

import com.orbanszlrd.quizapi.modules.question.model.Question;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean correct;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updatedAt;

    public Answer(String text, Boolean correct, Question question) {
        this.text = text;
        this.correct = correct;
        this.question = question;
    }

    public Answer(Long id, String text, Boolean correct, Question question) {
        this.id = id;
        this.text = text;
        this.correct = correct;
        this.question = question;
    }
}
