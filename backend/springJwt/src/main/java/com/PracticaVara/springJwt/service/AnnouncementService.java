package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
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

    public Announcement save(Announcement announcement, MultipartFile[] imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            announcement.setUser(user.get());
            LocalDateTime now = LocalDateTime.now();
            announcement.setCreatedDate(now);
            announcement.setExpirationDate(now.plusDays(60));

            if(imageFile != null && imageFile.length > 0){
                String folderUUID = UUID.randomUUID().toString();
                Path userDir = rootLocation.resolve(folderUUID);
                if(!Files.exists(userDir)){
                    Files.createDirectories(userDir);
                }

                int photoNumber = 0;
                for(int i = 0; i < imageFile.length; i++){
                    MultipartFile file = imageFile[i];
                    if(!file.isEmpty()){
                        String filename = folderUUID + "-" + i + "-" + file.getOriginalFilename();
                        Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
                        Files.copy(file.getInputStream(), destinationFile);
                        photoNumber++;
                    }
                }

                announcement.setImageUrl(userDir.toString());
                announcement.setPhotoNumber(photoNumber);
            } else {
                throw new RuntimeException("Please provide at least one image for the announcement.");
            }
            return announcementRepository.save(announcement);
        } else{
            throw new RuntimeException("User not found.");
        }

    }
    public void deleteById(Integer id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isPresent()) {
            String folderPath = announcement.get().getImageUrl();
            Path userDir = Paths.get(folderPath);
            if (Files.exists(userDir)) {
                try {
                    Files.walk(userDir)
                            .sorted((a, b) -> b.compareTo(a)) // to delete files before directories
                            .forEach(p -> {
                                try {
                                    Files.delete(p);
                                } catch (IOException e) {
                                    throw new RuntimeException("Error deleting file: " + p.getFileName(), e);
                                }
                            });
                } catch (IOException e) {
                    throw new RuntimeException("Error accessing the directory for deletion.", e);
                }
            }
            announcementRepository.deleteById(id);
        } else {
            throw new RuntimeException("Announcement with ID " + id + " not found.");
        }
    }
    public void deleteExpiredAnnouncements() {
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findByExpirationDateBefore(now);
        for (Announcement announcement : expiredAnnouncements) {
            deleteById(announcement.getId());
        }
    }

    public Announcement updateAnnouncement(Integer id, Announcement updatedAnnouncement, MultipartFile[] imageFiles) throws IOException {
        Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);
        if (existingAnnouncement.isPresent()) {
            Announcement announcement = existingAnnouncement.get();
            announcement.setTitle(updatedAnnouncement.getTitle());
            announcement.setContent(updatedAnnouncement.getContent());
            announcement.setExpirationDate(updatedAnnouncement.getExpirationDate());

            if (imageFiles != null && imageFiles.length > 0) {
                String folderUUID = UUID.randomUUID().toString();
                Path userDir = rootLocation.resolve(folderUUID);
                if (!Files.exists(userDir)) {
                    Files.createDirectories(userDir);
                }

                int photoNumber = 0;
                for (int i = 0; i < imageFiles.length; i++) {
                    MultipartFile file = imageFiles[i];
                    if (!file.isEmpty()) {
                        String filename = folderUUID + "-" + i + "-" + file.getOriginalFilename();
                        Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
                        Files.copy(file.getInputStream(), destinationFile);
                        photoNumber++;
                    }
                }

                announcement.setImageUrl(userDir.toString());
                announcement.setPhotoNumber(photoNumber);
            } else {
                throw new RuntimeException("Please provide at least one image for the announcement update.");
            }

            return announcementRepository.save(announcement);

        } else {
            throw new RuntimeException("Announcement not found");
        }
    }
    private String saveImage(MultipartFile file, User user){
        try {
            String folderUUID = UUID.randomUUID().toString();
            Path userDir = rootLocation.resolve(folderUUID);

            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            List<Path> userImages = Files.list(userDir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            if (userImages.size() >= 5) {
                throw new RuntimeException("You have reached the maximum number of 5 images.");
            }

            String filename = (userImages.size() + 1) + "-" + file.getOriginalFilename();
            Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);

            return destinationFile.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
