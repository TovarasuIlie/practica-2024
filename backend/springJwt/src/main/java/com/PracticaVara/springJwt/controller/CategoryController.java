package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Category;
import com.PracticaVara.springJwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/Categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("get-all-categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("get-category-by-id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add-category")
    public Category addCategory(@RequestPart("category") Category category, @RequestPart("file") MultipartFile file) throws IOException {
        return categoryService.addCategory(category, file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("edit-category/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestPart("category") Category categoryDetails, @RequestPart("file") MultipartFile file) throws IOException {
        return categoryService.updateCategory(id, categoryDetails, file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
