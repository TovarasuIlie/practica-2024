package com.PracticaVara.springJwt.controller.Announcements;

import com.PracticaVara.springJwt.DTOs.AnnouncementDTO;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementServices.AnnouncementService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "${spring.originUrl}")
@RestController
@RequestMapping("api/Announcements")
public class AnnouncementController {

    @Autowired
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

    @GetMapping("get-ads-by-category/{category-url}")
    public ResponseEntity<List<Announcement>> getAnnouncementByCategory(@PathVariable("category-url") String categoryUrl){
        return ResponseEntity.ok(announcementService.findByCategory(categoryUrl));
    }

    @GetMapping("get-ads-for-user/{username}")
    public ResponseEntity<List<Announcement>> getAnnouncementByUser(@PathVariable("username") String username){
        return ResponseEntity.ok(announcementService.findByUser(username));
    }

    @PostMapping(value = "create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createAnnouncement(@RequestPart("announcement") AnnouncementDTO announcement, @RequestPart("image") MultipartFile[] imageFile){
        try {
            return announcementService.save(announcement, imageFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }


    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable Integer id, @RequestBody AnnouncementDTO announcement) {
        //announcement.setId(id);
        try {
            return announcementService.updateAnnouncement(id, announcement);
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
    public ResponseEntity<?> deleteAnnouncementById(@PathVariable Integer id){
        return announcementService.deleteById(id);
    }

    @GetMapping("activate/{id}")
    public ResponseEntity<?> activateAnnouncement(@PathVariable Integer id) {
        return announcementService.activateAnnouncement(id);
    }

    @GetMapping("deactivate/{id}")
    public ResponseEntity<?> deactivateAnnouncement(@PathVariable Integer id) {
        return announcementService.dezactivateAnnouncement(id);
    }

}
