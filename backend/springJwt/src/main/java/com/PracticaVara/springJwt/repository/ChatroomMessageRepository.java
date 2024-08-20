package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Chatroom.ChatroomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomMessageRepository extends JpaRepository<ChatroomMessage, Integer> {
    Optional<List<ChatroomMessage>> findByChatroom_id(String chatroomId);
}
