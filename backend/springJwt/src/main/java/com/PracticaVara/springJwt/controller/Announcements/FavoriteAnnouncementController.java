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

    @GetMapping("check-wishlist/{id}")
    public ResponseEntity<Boolean> checkWishlist(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(favoriteAnnouncementService.checkAdAdded(id));
    }

    @GetMapping("add-to-wishlist/{id}")
    public ResponseEntity<?> addToWishlist(@PathVariable Integer id) {
        return favoriteAnnouncementService.addToFavorites(id);
    }

    @DeleteMapping("remove-from-wishlist/{id}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Integer id) {
        return favoriteAnnouncementService.removeFromFavorites(id);
    }
}
