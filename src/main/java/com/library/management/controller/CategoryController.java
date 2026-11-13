package com.library.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import com.library.management.entity.Category;
import com.library.management.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 1. Add Category
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {

        return categoryService.saveCategory(category);
    }

    // 2. Get All Categories
    @GetMapping
    public List<Category> getAllCategories() {

        return categoryService.getAllCategories();
    }

    // 3. Get Category By ID
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {

        try {
            return categoryService.getCategoryById(id);

        } catch (RuntimeException e) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category is not Available"
            );
        }
    }

    // 4. Update Category
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {

        try {
            return categoryService.updateCategory(id, category);

        } catch (RuntimeException e) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category is not Available"
            );
        }
    }

    // 5. Delete Category
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {

        try {
            categoryService.deleteCategory(id);

            return "Category deleted successfully";

        } catch (RuntimeException e) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category is not Available"
            );
        }
    }
}



