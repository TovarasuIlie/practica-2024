package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import jakarta.servlet.ServletContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
//    private final Path rootLocation = Paths.get("public/ad-imgs");
    private final Path rootLocation;
    {
        try {
            rootLocation = Paths.get(ServletContext.class.getClassLoader().getResource("public/ads-imgs").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Announcement> findAll(){
        return announcementRepository.findByIsApprovedTrueAndIsDeactivatedFalse();
    }

    public Optional<Announcement> findById(Integer id){
        return announcementRepository.findById(id);
    }

    public Optional<Announcement> findByUrl(String url) {
        return announcementRepository.findByUrl(url);
    }

    public ResponseEntity<Object> save(Announcement announcement, MultipartFile[] imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            announcement.setUser(user.get());
            LocalDateTime now = LocalDateTime.now();
            announcement.setCreatedDate(now);
            announcement.setExpirationDate(now.plusDays(60));
            announcement.setUrl(announcement.getTitle().toLowerCase().replaceAll("[$&+,:;=?@#|'<>.-^*()%! ]", "-"));

            if (imageFile != null && imageFile.length > 0) {
                String folderUUID = UUID.randomUUID().toString();
                Path userDir = rootLocation.resolve(folderUUID);
                if (!Files.exists(userDir)) {
                    Files.createDirectories(userDir);
                }
                //int photoNumber = 0;
                for(int i = 0; i < imageFile.length; i++){
                    MultipartFile file = imageFile[i];
                    if(!file.isEmpty()){
                        String filename = folderUUID + "-" + i + ".jpeg" ;
                        Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
                        Files.copy(file.getInputStream(), destinationFile);
                        //photoNumber++;
                    }
                }

                announcement.setImageUrl(folderUUID);
                announcement.setPhotoNumber(imageFile.length);
            } else {
                //throw new RuntimeException("Please provide at least one image for the announcement.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Anuntul trebuie sa contina minim o imagine"));
            }
            announcementRepository.save(announcement);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage(HttpStatus.OK, "Anuntul a fost creat."));
        } else {
            //throw new RuntimeException("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
    }

    public void deleteById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if(currentUser.isPresent()){
            Optional<Announcement> announcement = announcementRepository.findById(id);
            if (announcement.isPresent()) {
                if (!Objects.equals(announcement.get().getUser().getId(), currentUser.get().getId())){
                    throw new RuntimeException("Eroare! Poti sterge doar anunturile tale!");
                }
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
    }

    public ResponseEntity<Object> updateAnnouncement(Integer id, Announcement updatedAnnouncement, MultipartFile[] imageFiles) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);


        if(currentUser.isPresent()){
            Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);
            if(existingAnnouncement.isPresent()){
                if (existingAnnouncement.get().getUser().getId() != currentUser.get().getId()){
                    throw new RuntimeException("Eroare! Poti edita doar anunturile tale!");
                }

                Announcement announcement = existingAnnouncement.get();
                announcement.setTitle(updatedAnnouncement.getTitle());
                announcement.setContent(updatedAnnouncement.getContent());
                announcement.setExpirationDate(updatedAnnouncement.getExpirationDate());

                announcement.setApproved(false); //M-am gandit sa pun mereu ca nu e aprobat daca e modificat, sa stergi asta daca nu vrei sa fie asa

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
                    //throw new RuntimeException("Please provide at least one image for the announcement update.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Anuntul trebuie sa contina minim o poza"));
                }

                announcementRepository.save(announcement);
                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Anuntul a fost editat."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu exista"));
            }


        } else {
            //throw new RuntimeException("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
    }
    /*private String saveImage(MultipartFile file, User user){
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
    }*/

    public List<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitleContainingIgnoreCaseAndIsApprovedTrue(title);
    }


}
