package com.orbanszlrd.quizapi.modules.quiz;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.question.Question;
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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer timeLimit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updatedAt;

    public Quiz(Long id, String name, Integer timeLimit, Category category) {
        this.id = id;
        this.name = name;
        this.timeLimit = timeLimit;
        this.category = category;
    }

    public Quiz(String name, Integer timeLimit, Category category) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.category = category;
    }
}
