package com.orbanszlrd.quizapi.modules.category.controller;

import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.model.dto.CategoryResponse;
import com.orbanszlrd.quizapi.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public String findAll(Model model) {
        List<CategoryResponse> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/categories";
    }

    @GetMapping("/create")
    public String create(Model model) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        Category category = new Category(randomName);

        model.addAttribute("category", category);
        model.addAttribute("action", "/categories");
        model.addAttribute("method", "POST");

        return "category/edit-category";
    }

    @PostMapping("")
    public String add(@RequestParam("name") String name) {
        Category category = new Category(name);
        categoryService.add(category);

        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        CategoryResponse category = categoryService.findById(id);

        model.addAttribute("category", category);
        model.addAttribute("method", "PUT");
        model.addAttribute("action", "/categories/" + id);

        return "edit-category";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("name") String name) {
        categoryService.update(id, new Category(name));

        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

        return "redirect:/categories";
    }
}
