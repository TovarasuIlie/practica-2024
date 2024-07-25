package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Announcement> announcement = announcementService.findByid(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestParam Integer userId, @RequestBody Announcement announcement){
        return ResponseEntity.ok(announcementService.save(announcement, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement){
        announcement.setId(id);
        return ResponseEntity.ok(announcementService.save(announcement, announcement.getUser().getId()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncementById(@PathVariable Integer id){
        announcementService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

}
