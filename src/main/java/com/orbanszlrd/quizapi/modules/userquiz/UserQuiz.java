package com.orbanszlrd.quizapi.modules.userquiz;

import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class UserQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updatedAt;

    public UserQuiz(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
    }

    public UserQuiz(Long id, User user, Quiz quiz) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
    }
}
