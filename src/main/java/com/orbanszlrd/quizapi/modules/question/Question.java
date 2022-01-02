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
@AllArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer timeLimit;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    private Byte value;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
