package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementService;

import org.springframework.http.MediaType;
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

    @GetMapping("get-my-ads")
    public ResponseEntity<List<Announcement>> getMyAnnouncements(){
        return announcementService.getMyAds();
    }

    @GetMapping("get-ad-by-id/{id}")
    public ResponseEntity<Announcement> getAnnouncementByUrl(@PathVariable Integer id){
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

    @PostMapping(value = "create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createAnnouncement(@RequestPart("announcement") Announcement announcement, @RequestPart("image") MultipartFile[] imageFile){
        try {
            return announcementService.save(announcement, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }


    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image") MultipartFile[] imageFile) {
        announcement.setId(id);
        try {
            return announcementService.updateAnnouncement(id, announcement, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }

    @GetMapping("search-for-ads")
    public ResponseEntity<List<Announcement>> searchAnnouncements(@RequestParam String title){
        List<Announcement> announcements = announcementService.findByTitle(title);

        if (announcements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcements);
    }


    @DeleteMapping("delete-ad/{id}")
    public ResponseEntity<Void> deleteAnnouncementById(@PathVariable Integer id){
        announcementService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

}
