package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.FavoriteAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteAnnouncementRepository extends JpaRepository<FavoriteAnnouncement, Integer> {
    Optional<FavoriteAnnouncement> findByAnnouncementId(Integer id);
    Optional<FavoriteAnnouncement> findByUserAndAnnouncement(User user, Announcement announcement);
    List<FavoriteAnnouncement> findByAnnouncement(Announcement announcement);
    List<FavoriteAnnouncement> findAllByUser(User user);
}
