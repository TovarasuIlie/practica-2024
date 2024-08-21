package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Chatroom.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {
    Optional<Chatroom> findByAnnouncement_idAndSeller_idAndBuyer_id(Integer announcementId, Integer sellerId, Integer buyerId);
    Optional<List<Chatroom>> findBySeller_id(Integer sellerId);
    Optional<List<Chatroom>> findByBuyer_id(Integer sellerId);
}
