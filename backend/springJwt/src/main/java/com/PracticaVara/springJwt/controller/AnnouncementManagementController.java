package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementManagementService;
import com.PracticaVara.springJwt.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/Announcements-management")
public class AnnouncementManagementController {
    private final AnnouncementManagementService announcementManagementService;

    public AnnouncementManagementController(AnnouncementManagementService announcementManagementService) {
        this.announcementManagementService = announcementManagementService;
    }

    @GetMapping("get-all-ads")
    public ResponseEntity<List<Announcement>> getAllAnnouncements(){
        return ResponseEntity.ok(announcementManagementService.findAll());
    }

    @GetMapping("get-ad-by-id/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer id){
        Optional<Announcement> announcement = announcementManagementService.findById(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("get-ad-by-url/{url}")
    public ResponseEntity<Announcement> getAnnouncementByUrl(@PathVariable String url){
        Optional<Announcement> announcement = announcementManagementService.findByUrl(url);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createAnnouncement(@RequestPart("announcement") Announcement announcement, @RequestPart("image") MultipartFile[] imageFile){
        try {
            return ResponseEntity.ok(announcementManagementService.save(announcement, imageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }


    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image") MultipartFile[] imageFile) {
        announcement.setId(id);
        try {
            return ResponseEntity.ok(announcementManagementService.updateAnnouncement(id, announcement, imageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }


    @GetMapping("search")
    public ResponseEntity<List<Announcement>> searchAnnouncements(@RequestParam String title){
        List<Announcement> announcements = announcementManagementService.findByTitle(title);

        if (announcements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("approve/{id}")
    public ResponseEntity<?> approveAnnouncement(@PathVariable Integer id) {
        try {
            announcementManagementService.approveAnnouncement(id);
            return ResponseEntity.ok("Anuntul a fost aprobat.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("reject/{id}")
    public ResponseEntity<?> rejectAnnouncement(@PathVariable Integer id) {
        try {
            announcementManagementService.rejectAnnouncement(id);
            return ResponseEntity.ok("Anuntul a fost rejectat.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete-ad/{id}")
    public ResponseEntity<Void> deleteAnnouncementById(@PathVariable Integer id){
        announcementManagementService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
}
