package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByExpirationDateBefore(LocalDateTime now);
    List<Announcement> findByUser(User user);
    List<Announcement> findByUserAndIsApprovedTrueAndIsDeactivatedFalse(User user);
    List<Announcement> findByIsApprovedTrue();
    List<Announcement> findByIsApprovedFalse();
    List<Announcement> findByTitleContainingIgnoreCaseAndIsApprovedTrue(String title);
    List<Announcement> findByIsApprovedTrueAndIsDeactivatedFalse();
    Optional<Announcement> findByUrl(String url);
    Long countByIsApprovedFalseAndIsDeactivatedFalse();
    List<Announcement> findByCategory(Category category);
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM announcements WHERE CAST(announcements.created_date AS DATE) = CURRENT_DATE")
    Long countTodayAnnouncements();
    @Query(nativeQuery = true, value = "SELECT * FROM announcements LIMIT 100")
    List<Announcement> last100Ads();
}
