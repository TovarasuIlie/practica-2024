package com.PracticaVara.springJwt.controller.Announcements;

import com.PracticaVara.springJwt.service.AnnouncementServices.FavoriteAnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${spring.originUrl}")
@RestController
@RequestMapping("api/Wishlist")
public class FavoriteAnnouncementController {
    private final FavoriteAnnouncementService favoriteAnnouncementService;
    public FavoriteAnnouncementController(FavoriteAnnouncementService favoriteAnnouncementService) {
        this.favoriteAnnouncementService = favoriteAnnouncementService;
    }
    @GetMapping("get-wishlist")
    public ResponseEntity<?> getWishlist() {
        return favoriteAnnouncementService.showCurrentUserWishlist();
    }

    @PostMapping("add-to-wishlist")
    public ResponseEntity<?> addToWishlist(@RequestParam Integer announcementId) {
        return favoriteAnnouncementService.addToFavorites(announcementId);
    }

    @DeleteMapping
    public ResponseEntity<?> removeFromWishlist(@RequestParam Integer announcementId) {
        return favoriteAnnouncementService.removeFromFavorites(announcementId);
    }

}
