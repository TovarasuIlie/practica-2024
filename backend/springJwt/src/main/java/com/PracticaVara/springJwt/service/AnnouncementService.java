package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final Path rootLocation = Paths.get("uploads");

    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }
    public Optional<Announcement> findByid(Integer id){
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement, Integer userId, MultipartFile imageFile){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            announcement.setUser(user.get());
            LocalDateTime now = LocalDateTime.now();
            announcement.setCreatedDate(now);
            announcement.setExpirationDate(now.plusDays(60));

            if(imageFile != null && !imageFile.isEmpty()){
                String imageUrl = saveImage(imageFile, user.get());
                announcement.setImageUrl(imageUrl);
            }
            return announcementRepository.save(announcement);
        } else{
            throw new RuntimeException("User not found.");
        }

    }
    public void deleteById(Integer id){
        announcementRepository.deleteById(id);
    }
    public void deleteExpiredAnnouncements(){
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findByExpirationDateBefore(now);
        announcementRepository.deleteAll(expiredAnnouncements);
    }
    private String saveImage(MultipartFile file, User user){
        try{

            Path userDir = rootLocation.resolve("user-" + user.getId());
            if(!Files.exists(userDir)){
                Files.createDirectories(userDir);
            }

            List<Path> userImages = Files.list(userDir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            if(userImages.size() >= 5){
                throw new RuntimeException("User has reached the maximum number of 5 images");
            }

            String filename = (userImages.size() + 1) + "-" + file.getOriginalFilename();
            Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);

            return destinationFile.toString();

        } catch (IOException e){
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
