package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {


}
