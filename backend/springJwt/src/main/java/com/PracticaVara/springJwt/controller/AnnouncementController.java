package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementService;
import lombok.SneakyThrows;
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

    @GetMapping("get-ad-by-id/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer id){
        Optional<Announcement> announcement = announcementService.findByid(id);
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
    @PostMapping(value = "create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Announcement> createAnnouncement(@RequestPart("announcement") Announcement announcement, @RequestPart("image") MultipartFile[] imageFile){
        return ResponseEntity.ok(announcementService.save(announcement, imageFile));
    }

    @SneakyThrows
    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement, @RequestParam("image") MultipartFile[] imageFile){
        announcement.setId(id);
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, announcement, imageFile));

    }

    @DeleteMapping("delete-ad/{id}")
    public ResponseEntity<Void> deleteAnnouncementById(@PathVariable Integer id){
        announcementService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

}
