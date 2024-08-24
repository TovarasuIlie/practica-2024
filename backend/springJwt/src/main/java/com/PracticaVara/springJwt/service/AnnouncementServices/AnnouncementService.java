package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.DTOs.AnnouncementDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
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

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final LogHistoryRepository logHistoryRepository;
    private final Path rootLocation;
    {
        try {
            rootLocation = Paths.get(ServletContext.class.getClassLoader().getResource("public/ads-imgs").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository, LogHistoryRepository logHistoryRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.logHistoryRepository = logHistoryRepository;
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

    public ResponseEntity<Object> save(AnnouncementDTO announcementDTO, MultipartFile[] imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        Announcement announcement = new Announcement();

        if (currentUser.isPresent()) {
            User user = currentUser.get();
            announcement.setUser(user);
            announcement.setTitle(announcementDTO.getTitle());
            announcement.setContent(announcementDTO.getContent());
            announcement.setCategory(announcementDTO.getCategory());
            announcement.setPrice(announcementDTO.getPrice());
            announcement.setCurrency(announcementDTO.getCurrency());
            announcement.setAddress(announcementDTO.getAddress());
            announcement.setContactPersonName(announcementDTO.getContactPersonName());
            announcement.setPhoneNumber(announcementDTO.getPhoneNumber());
            LocalDateTime now = LocalDateTime.now();
            announcement.setCreatedDate(now);
            announcement.setExpirationDate(now.plusDays(60));
            announcement.setUrl(announcementDTO.getTitle().toLowerCase().replaceAll("[\\p{P}\\p{S}&&[^$%^*+=,./<>_-]]|[$%^*+=,./<>_-](?!(?<=\\d.)\\d)", "").replaceAll(" ", "-"));

            if (imageFile != null && imageFile.length > 1) {
                String folderUUID = UUID.randomUUID().toString();
                Path userDir = rootLocation.resolve(folderUUID);
                if (!Files.exists(userDir)) {
                    Files.createDirectories(userDir);
                }

                for(int i = 0; i < imageFile.length; i++){
                    MultipartFile file = imageFile[i];
                    if(!file.isEmpty()){
                        String filename = folderUUID + "-" + i + ".jpeg" ;
                        Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
                        Files.copy(file.getInputStream(), destinationFile);
                    }
                }

                announcement.setImageUrl(folderUUID);
                announcement.setPhotoNumber(imageFile.length);
            } else {
                //throw new RuntimeException("Please provide at least one image for the announcement.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Anuntul trebuie sa contina minim 2 poze."));
            }
            announcementRepository.save(announcement);

            LogHistory newLogHistory = new LogHistory();
            newLogHistory.setUser(user);
            newLogHistory.setAction("A creat anuntul cu id-ul " +announcement.getId());
            newLogHistory.setIpAddress(user.getIpAddress());
            newLogHistory.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistory);

            return ResponseEntity.status(HttpStatus.CREATED).body(announcement);
        } else {
            //throw new RuntimeException("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
    }

    public ResponseEntity<?> deleteById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if(currentUser.isPresent()){
            Optional<Announcement> announcement = announcementRepository.findById(id);
            User user = currentUser.get();
            if (announcement.isPresent()) {
                Announcement announcement1 = announcement.get();
                if (!Objects.equals(announcement.get().getUser().getId(), currentUser.get().getId())){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Poti sterge doar anunturile tale."));
                }
                String folderPath = announcement.get().getImageUrl();
                Path userDir = rootLocation.resolve(folderPath);

                if (Files.exists(userDir)) {
                    try {
                        Files.walk(userDir)
                                .sorted((a, b) -> b.compareTo(a))
                                .forEach(p -> {
                                    try {
                                        Files.delete(p);
                                        ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Anuntul a fost sters cu succes."));
                                    } catch (IOException e) {
                                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare la stergerea fisierului: "+ p.getFileName()));
                                    }
                                });
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Eroare la accesarea fisierelor pentru stergere."));
                    }
                }

                LogHistory newLogHistory = new LogHistory();
                newLogHistory.setUser(user);
                newLogHistory.setAction("A sters anuntul cu id-ul " +announcement1.getId());
                newLogHistory.setIpAddress(user.getIpAddress());
                newLogHistory.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistory);

                announcementRepository.deleteById(id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu a fost gasit." ));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare interna"));
    }

    public ResponseEntity<Object> updateAnnouncement(Integer id, AnnouncementDTO updatedAnnouncement, MultipartFile[] imageFiles) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);


        if(currentUser.isPresent()){
            User user = currentUser.get();
            Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);
            if(existingAnnouncement.isPresent()){
                if (existingAnnouncement.get().getUser().getId() != currentUser.get().getId()){
                    throw new RuntimeException("Eroare! Poti edita doar anunturile tale!");
                }

                Announcement announcement = existingAnnouncement.get();
                announcement.setTitle(updatedAnnouncement.getTitle());
                announcement.setContent(updatedAnnouncement.getContent());
                announcement.setPrice(updatedAnnouncement.getPrice());
                announcement.setCurrency(updatedAnnouncement.getCurrency());
                announcement.setAddress(updatedAnnouncement.getAddress());
                announcement.setContactPersonName(updatedAnnouncement.getContactPersonName());
                announcement.setPhoneNumber(updatedAnnouncement.getPhoneNumber());
                announcement.setUrl(updatedAnnouncement.getTitle().toLowerCase().replaceAll("[\\p{P}\\p{S}&&[^$%^*+=,./<>_-]]|[$%^*+=,./<>_-](?!(?<=\\d.)\\d)", "").replaceAll(" ", "-"));
                announcement.setExpirationDate(LocalDateTime.now().plusDays(60));
                //announcement.setExpirationDate(updatedAnnouncement.getExpirationDate());
                announcement.setApproved(false); //M-am gandit sa pun mereu ca nu e aprobat daca e modificat, sa stergi asta daca nu vrei sa fie asa

                if (imageFiles != null && imageFiles.length > 1) {
                    String folderUUID = UUID.randomUUID().toString();
                    Path userDir = rootLocation.resolve(folderUUID);
                    if (!Files.exists(userDir)) {
                        Files.createDirectories(userDir);
                    }

                    for (int i = 0; i < imageFiles.length; i++) {
                        MultipartFile file = imageFiles[i];
                        if (!file.isEmpty()) {
                            String filename = folderUUID + "-" + i + "-" + file.getOriginalFilename();
                            Path destinationFile = userDir.resolve(Paths.get(filename)).normalize().toAbsolutePath();
                            Files.copy(file.getInputStream(), destinationFile);
                        }
                    }

                    announcement.setImageUrl(userDir.toString());
                    announcement.setPhotoNumber(imageFiles.length);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Anuntul trebuie sa contina minim 2 poze."));
                }

                announcementRepository.save(announcement);

                LogHistory newLogHistory = new LogHistory();
                newLogHistory.setUser(user);
                newLogHistory.setAction("A editat anuntul cu id-ul " +announcement.getId());
                newLogHistory.setIpAddress(user.getIpAddress());
                newLogHistory.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistory);

                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Anuntul a fost editat."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu exista"));
            }


        } else {
            //throw new RuntimeException("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
    }
    public ResponseEntity<List<Announcement>> getMyAds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(announcementRepository.findByUser(user.get()));
    }
    public List<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitleContainingIgnoreCaseAndIsApprovedTrue(title);
    }


}
