package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.FavoriteAnnouncement;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.FavoriteAnnouncementRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteAnnouncementService {
    private final FavoriteAnnouncementRepository favoriteAnnouncementRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    public FavoriteAnnouncementService(FavoriteAnnouncementRepository favoriteAnnouncementRepository, UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.favoriteAnnouncementRepository = favoriteAnnouncementRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
    }

    public ResponseEntity<?> addToFavorites(Integer announcementId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()) {
            User user = currentUser.get();
            Optional<Announcement> currentAnnouncement = announcementRepository.findById(announcementId);

            if(currentAnnouncement.isPresent()) {
                Announcement announcement = currentAnnouncement.get();
                FavoriteAnnouncement favoriteAnnouncement = new FavoriteAnnouncement();
                favoriteAnnouncement.setAnnouncement(announcement);
                favoriteAnnouncement.setUser(user);
                favoriteAnnouncement.setAddedToFavoritesDate(LocalDateTime.now());
                favoriteAnnouncementRepository.save(favoriteAnnouncement);
                return ResponseEntity.status(HttpStatus.CREATED).body(favoriteAnnouncement);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu a fost gasit."));

            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
        }
    }

    public ResponseEntity<?> removeFromFavorites(Integer announcementId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()) {
            User user = currentUser.get();
            Optional<Announcement> currentAnnouncement = announcementRepository.findById(announcementId);
            if(currentAnnouncement.isPresent()) {
                Announcement announcement = currentAnnouncement.get();
                Optional<FavoriteAnnouncement> favoriteAnnouncement = favoriteAnnouncementRepository.findByUserAndAnnouncement(user, announcement);
                if(favoriteAnnouncement.isPresent()) {
                    favoriteAnnouncementRepository.delete(favoriteAnnouncement.get());
                    return ResponseEntity.status(HttpStatus.OK).body(true);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul din lista de favorite nu a fost gasit."));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu a fost gasit."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
        }
    }

    public ResponseEntity<?> showCurrentUserWishlist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()) {
            User user = currentUser.get();
            List<FavoriteAnnouncement> favoriteAnnouncements = favoriteAnnouncementRepository.findAllByUser(user);
            List<Announcement> announcements  = favoriteAnnouncements.stream()
                    .map(FavoriteAnnouncement::getAnnouncement)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(announcements);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
        }
    }

    public Boolean checkAdAdded(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userRepository.findByUsername(authentication.getName());
        Optional<Announcement> currentAd = announcementRepository.findById(id);
        if(favoriteAnnouncementRepository.findByUserAndAnnouncement(currentUser.get(), currentAd.get()).isPresent()) {
            return true;
        }
        return false;
    }
}
