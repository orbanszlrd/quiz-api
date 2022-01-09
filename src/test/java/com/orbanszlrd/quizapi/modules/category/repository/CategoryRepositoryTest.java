package com.orbanszlrd.quizapi.modules.category.repository;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Sql("classpath:data/sql/insertCategories.sql")
    void findAll() {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories.size()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Programming", "Nature"})
    @Sql("classpath:data/sql/insertCategories.sql")
    void findByName(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        assertThat(categoryOptional.isPresent()).isTrue();
    }
}