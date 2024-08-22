package com.PracticaVara.springJwt.controller.Announcements;

import com.PracticaVara.springJwt.DTOs.AnnouncementDTO;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.service.AnnouncementServices.AnnouncementManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${spring.originUrl}")
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

    @PutMapping("edit-ad/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable Integer id, @RequestBody AnnouncementDTO announcement, @RequestParam("image") MultipartFile[] imageFile) {
        //announcement.setId(id);
        try {
            return ResponseEntity.ok(announcementManagementService.updateAnnouncement(id, announcement, imageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("A avut loc o eroare!");
    }

    @GetMapping("approve/{id}")
    public ResponseEntity<?> approveAnnouncement(@PathVariable Integer id) {
        return announcementManagementService.approveAnnouncement(id);
    }

    @GetMapping("reject/{id}")
    public ResponseEntity<?> rejectAnnouncement(@PathVariable Integer id) {
        return announcementManagementService.rejectAnnouncement(id);
    }

    @DeleteMapping("delete-ad/{id}")
    public ResponseEntity<?> deleteAnnouncementById(@PathVariable Integer id){
        return announcementManagementService.deleteById(id);

    }
}
