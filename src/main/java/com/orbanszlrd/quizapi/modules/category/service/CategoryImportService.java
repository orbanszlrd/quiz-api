package com.orbanszlrd.quizapi.modules.category.service;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImportService {
    private final CategoryRepository categoryRepository;

    @Bean
    public CommandLineRunner importData() {
        return args -> {
            List<Category> categories = List.of(
                    new Category(1L, "Nature"),
                    new Category(2L, "Programming"),
                    new Category(3L, "Sport"),
                    new Category(4L, "Politics"),
                    new Category(5L, "Business")
            );

            categoryRepository.saveAll(categories);
        };
    }
}
