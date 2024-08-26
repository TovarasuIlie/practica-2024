package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.DTOs.CategoryDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Category;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.CategoryRepository;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    private final LogHistoryRepository logHistoryRepository;
    private final Path rootLocation = Paths.get("public/category-imgs");;
    {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CategoryService(LogHistoryRepository logHistoryRepository) {
        this.logHistoryRepository = logHistoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public ResponseEntity<?> addCategory(CategoryDTO categoryDTO, MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        Category category = new Category();

        if (!file.isEmpty() && currentUser.isPresent()) {
            String name = categoryDTO.getName().toLowerCase().replaceAll("[\\p{P}\\p{S}&&[^$%^*+=,./<>_-]]|[$%^*+=,./<>_-](?!(?<=\\d.)\\d)", "").replaceAll(" ", "-");
            Path destinationFile = rootLocation.resolve(Paths.get(name + ".png")).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            category.setName(categoryDTO.getName());
            category.setIconUrl(name + ".png");
            category.setSearchLink(name);
            categoryRepository.save(category);

            User admin = currentUser.get();
            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(admin);
            newLogHistoryAdmin.setAction("A creat categoria cu id-ul " + category.getId());
            newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);

            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Fisierul nu a fost gasit"));
        }

    }

    public ResponseEntity<?> updateCategory(Long id, CategoryDTO categoryDetailsDTO, MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent() && currentUser.isPresent()) {
            Category category = categoryOptional.get();
            if(!file.isEmpty()) {
                File oldImageFile = new File(rootLocation.toString() + "/" + category.getIconUrl());
                oldImageFile.delete();
            }
            String name = categoryDetailsDTO.getName().toLowerCase().replaceAll("[\\p{P}\\p{S}&&[^$%^*+=,./<>_-]]|[$%^*+=,./<>_-](?!(?<=\\d.)\\d)", "").replaceAll(" ", "-");
            category.setName(categoryDetailsDTO.getName());
            category.setSearchLink(category.getSearchLink());
            category.setIconUrl(name + ".png");
            Path destinationFile = rootLocation.resolve(Paths.get(name + ".png")).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            categoryRepository.save(category);
            User admin = currentUser.get();
            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(admin);
            newLogHistoryAdmin.setAction("A editat categoria cu id-ul " + category.getId());
            newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Categoria a fost editata cu succes!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Categoria nu a fost gasita."));
        }

    }

    public ResponseEntity<?> deleteCategory(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentAdmin = userRepository.findByUsername(username);
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent() && currentAdmin.isPresent()) {
            Category category = categoryOptional.get();
            User admin = currentAdmin.get();
            File imageFile = new File(rootLocation.toString() + "/" + category.getIconUrl());
            imageFile.delete();
            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(admin);
            newLogHistoryAdmin.setAction("A sters categoria cu id-ul " + category.getId());
            newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);
            categoryRepository.delete(category);
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Categoria a fost stearsa cu succes."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Categoria nu a fost gasita."));
        }
    }
}
