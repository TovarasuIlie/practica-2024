package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.DTOs.AnnouncementDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.PracticaVara.springJwt.service.AccountServices.AccountService;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class AnnouncementManagementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final Path rootLocation = Paths.get("public/ads-imgs");
    private final LogHistoryRepository logHistoryRepository;
    {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnnouncementManagementService(AnnouncementRepository announcementRepository, UserRepository userRepository, LogHistoryRepository logHistoryRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.logHistoryRepository = logHistoryRepository;
    }

    public ResponseEntity<Object> approveAnnouncement(Integer id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if(announcement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul cu ID " + id + " nu a fost gasit"));
        }
        announcement.get().setApproved(true);
        announcementRepository.save(announcement.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new APIMessage(HttpStatus.ACCEPTED, "Anuntul a fost aprobat cu succes!"));
    }

    public ResponseEntity<Object> rejectAnnouncement(Integer id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if(announcement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul cu ID " + id + " nu a fost gasit"));
        }
        announcement.get().setApproved(false);
        announcementRepository.save(announcement.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new APIMessage(HttpStatus.ACCEPTED, "Anuntul a fost respins cu succes!"));
    }

    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }
    public Optional<Announcement> findById(Integer id){
        return announcementRepository.findById(id);
    }

    public ResponseEntity<?> deleteById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()){
            User admin = currentUser.get();
            Optional<Announcement> announcement = announcementRepository.findById(id);
            if (announcement.isPresent()) {
                String folderPath = announcement.get().getImageUrl();
                Path userDir = rootLocation.resolve(folderPath);

                if (Files.exists(userDir)) {
                    try {
                        Files.walk(userDir)
                                .sorted((a, b) -> b.compareTo(a)) // to delete files before directories
                                .forEach(p -> {
                                    try {
                                        Files.delete(p);
                                    } catch (IOException e) {
                                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare la stergerea fisierului: " + p.getFileName()));
                                    }
                                });
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Eroare la accesarea fisierelor pentru stergere."));
                    }
                }
                announcementRepository.deleteById(id);

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A sters anuntul cu id-ul " +announcement.get().getId());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                LogHistory newLogHistoryUser = new LogHistory();
                newLogHistoryUser.setUser(announcement.get().getUser());
                newLogHistoryUser.setAction("A avut anuntul cu id-ul " +announcement.get().getId()+ " sters de catre administratorul " +admin.getUsername());
                newLogHistoryUser.setIpAddress(announcement.get().getUser().getIpAddress());
                newLogHistoryUser.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryUser);


                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Anuntul a fost sters cu succes."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu a fost gasit." ));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista"));
        }
    }
    public void deleteExpiredAnnouncements() {
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findByExpirationDateBefore(now);
        for (Announcement announcement : expiredAnnouncements) {
            deleteById(announcement.getId()); // aici trebuie modificat
        }
    }

    public Announcement updateAnnouncement(Integer id, AnnouncementDTO updatedAnnouncement, MultipartFile[] imageFiles) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if(currentUser.isPresent()){
            User admin = currentUser.get();
            Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);

            if (existingAnnouncement.isPresent()) {
                Announcement announcement = existingAnnouncement.get();
                announcement.setTitle(updatedAnnouncement.getTitle());
                announcement.setContent(updatedAnnouncement.getContent());
                announcement.setPrice(updatedAnnouncement.getPrice());
                announcement.setCurrency(updatedAnnouncement.getCurrency());
                announcement.setAddress(updatedAnnouncement.getAddress());
                announcement.setContactPersonName(updatedAnnouncement.getContactPersonName());
                announcement.setPhoneNumber(updatedAnnouncement.getPhoneNumber());
                announcement.setExpirationDate(LocalDateTime.now().plusDays(60));
                announcement.setUrl(updatedAnnouncement.getTitle().toLowerCase().replaceAll("[\\p{P}\\p{S}&&[^$%^*+=,./<>_-]]|[$%^*+=,./<>_-](?!(?<=\\d.)\\d)", "").replaceAll(" ", "-") + "-" + AccountService.getSaltString().toLowerCase());
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
                    throw new RuntimeException("Please provide at least one image for the announcement update.");
                }

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A editat anuntul cu id-ul " + announcement.getId());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                LogHistory newLogHistoryUser = new LogHistory();
                newLogHistoryUser.setUser(announcement.getUser());
                newLogHistoryUser.setAction("A avut anuntul cu id-ul " + announcement.getId() + " editat de catre administratorul " +admin.getUsername());
                newLogHistoryUser.setIpAddress(announcement.getUser().getIpAddress());
                newLogHistoryUser.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryUser);

                return announcementRepository.save(announcement);

            } else {
                throw new RuntimeException("Announcement not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
