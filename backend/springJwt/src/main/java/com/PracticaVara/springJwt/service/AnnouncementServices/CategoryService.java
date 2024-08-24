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

    private final Path rootLocation;
    @Autowired
    private UserRepository userRepository;
    private final LogHistoryRepository logHistoryRepository;

    {
        try {
            rootLocation = Paths.get(ServletContext.class.getClassLoader().getResource("public/category-imgs").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final String uploadDir = "uploads/";

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
//            File dest = new File(uploadDir + imageName);
//            file.transferTo(dest);
//            category.setIconUrl(imageName);
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
            User admin = currentUser.get();
            category.setName(categoryDetailsDTO.getName());
            category.setSearchLink(category.getSearchLink());

            if (!file.isEmpty()) {
                String imageName = category.getName().replaceAll("[^a-zA-Z0-9]", "-").toLowerCase() + ".png";
                File dest = new File(uploadDir + imageName);
                file.transferTo(dest);
                category.setIconUrl(imageName);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Imaginea nu a fost gasita."));
            }

            categoryRepository.save(category);

            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(admin);
            newLogHistoryAdmin.setAction("A editat categoria cu id-ul " + category.getId());
            newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);


            return ResponseEntity.status(HttpStatus.OK).body(category);
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
            File file = new File(uploadDir + category.getIconUrl());
            if (file.exists()) {
                file.delete();

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A sters categoria cu id-ul " + category.getId());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                categoryRepository.delete(category);

                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Categoria a fost stearsa cu succes."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Fisierele nu au fost gasite."));
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Categoria nu a fost gasita."));
        }
    }
}
