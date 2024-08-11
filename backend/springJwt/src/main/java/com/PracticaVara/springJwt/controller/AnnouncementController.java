package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementService;
import lombok.SneakyThrows;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
=======
import org.springframework.http.MediaType;
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/Announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("get-all-ads")
    public ResponseEntity<List<Announcement>> getAllAnnouncements(){
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("get-ad-by-id/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer id){
        Optional<Announcement> announcement = announcementService.findById(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("get-ad-by-url/{url}")
    public ResponseEntity<Announcement> getAnnouncementByUrl(@PathVariable String url){
        Optional<Announcement> announcement = announcementService.findByUrl(url);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @SneakyThrows
<<<<<<< HEAD
    @PostMapping("/user/create-announcement")
    public ResponseEntity<Announcement> createAnnouncement(@RequestParam Integer userId, @RequestBody Announcement announcement, @RequestParam("image")MultipartFile[] imageFile){
=======
    @PostMapping(value = "create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Announcement> createAnnouncement(@RequestPart("announcement") Announcement announcement, @RequestPart("image") MultipartFile[] imageFile){
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
        return ResponseEntity.ok(announcementService.save(announcement, imageFile));
    }

    @SneakyThrows
<<<<<<< HEAD
    @PutMapping("/user/update-announcement/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image")MultipartFile[] imageFile){
=======
    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image") MultipartFile[] imageFile){
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
        announcement.setId(id);
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, announcement, imageFile));

    }

<<<<<<< HEAD
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
=======
    @DeleteMapping("delete-ad/{id}")
    public ResponseEntity<Void> deleteAnnouncementById(@PathVariable Integer id){
        announcementService.deleteById(id);
        return  ResponseEntity.noContent().build();
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
    }

}
