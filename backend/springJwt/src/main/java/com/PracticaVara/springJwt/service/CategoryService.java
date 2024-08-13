package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Category;
import com.PracticaVara.springJwt.repository.CategoryRepository;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final Path rootLocation;
    {
        try {
            rootLocation = Paths.get(ServletContext.class.getClassLoader().getResource("public/category-imgs").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final String uploadDir = "uploads/";

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category addCategory(Category category, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String imageName = category.getName().replaceAll("[^a-zA-Z0-9]", "-").toLowerCase() + ".png";
//            File dest = new File(uploadDir + imageName);
//            file.transferTo(dest);
//            category.setIconUrl(imageName);
            Path destinationFile = rootLocation.resolve(Paths.get(imageName)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            category.setIconUrl(imageName);
            category.setSearchLink(category.getName().replaceAll("[^a-zA-Z0-9]", "-").toLowerCase());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());
        category.setSearchLink(categoryDetails.getSearchLink());

        if (!file.isEmpty()) {
            String imageName = category.getName().replaceAll("[^a-zA-Z0-9]", "-").toLowerCase() + ".png";
            File dest = new File(uploadDir + imageName);
            file.transferTo(dest);
            category.setIconUrl(imageName);
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        File file = new File(uploadDir + category.getIconUrl());
        if (file.exists()) {
            file.delete();
        }

        categoryRepository.delete(category);
    }
}
