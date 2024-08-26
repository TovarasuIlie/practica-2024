package com.PracticaVara.springJwt.controller.Announcements;

import com.PracticaVara.springJwt.DTOs.CategoryDTO;
import com.PracticaVara.springJwt.model.Category;
import com.PracticaVara.springJwt.service.AnnouncementServices.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "${spring.originUrl}")
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

    @PostMapping(value = "add-category", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addCategory(@RequestPart("category") CategoryDTO category, @RequestPart("image") MultipartFile image) throws IOException {
        return categoryService.addCategory(category, image);
    }

    @PostMapping("edit-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestPart("category") CategoryDTO categoryDetails, @RequestPart("image") MultipartFile file) throws IOException {
       return categoryService.updateCategory(id, categoryDetails, file);
    }

    @DeleteMapping("delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
