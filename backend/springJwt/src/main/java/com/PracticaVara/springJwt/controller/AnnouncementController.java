package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/Announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements(){
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer id){
        Optional<Announcement> announcement = announcementService.findById(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @SneakyThrows
    @PostMapping("/user/create-announcement")
    public ResponseEntity<Announcement> createAnnouncement(@RequestParam Integer userId, @RequestBody Announcement announcement, @RequestParam("image")MultipartFile[] imageFile){
        return ResponseEntity.ok(announcementService.save(announcement, imageFile));
    }

    @SneakyThrows
    @PutMapping("/user/update-announcement/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image")MultipartFile[] imageFile){
        announcement.setId(id);
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, announcement, imageFile));

    }

    @DeleteMapping("/user/delete-announcement/{id}")
    public ResponseEntity<?> deleteAnnouncementById(@PathVariable Integer id){
        try {
            announcementService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<Announcement>> searchAnnouncements(@RequestParam String title){
        List<Announcement> announcements = announcementService.findByTitle(title);

        if (announcements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveAnnouncement(@PathVariable Integer id) {
        try {
            announcementService.approveAnnouncement(id);
            return ResponseEntity.ok("Announcement approved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectAnnouncement(@PathVariable Integer id) {
        try {
            announcementService.rejectAnnouncement(id);
            return ResponseEntity.ok("Announcement rejected successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
