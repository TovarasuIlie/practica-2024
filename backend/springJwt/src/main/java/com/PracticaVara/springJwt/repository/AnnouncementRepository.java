package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByExpirationDateBefore(LocalDateTime now);
}
