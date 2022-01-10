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
    public CommandLineRunner categoryImporter() {
        return args -> {
            List<Category> categories = List.of(
                    new Category("Nature"),
                    new Category("Programming"),
                    new Category("Sport"),
                    new Category("Politics"),
                    new Category("Business")
            );

            categoryRepository.saveAll(categories);
        };
    }
}
