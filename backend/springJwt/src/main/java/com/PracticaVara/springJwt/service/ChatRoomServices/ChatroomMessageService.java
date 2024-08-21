package com.PracticaVara.springJwt.service.ChatRoomServices;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Chatroom.ChatroomMessage;
import com.PracticaVara.springJwt.repository.ChatroomMessageRepository;
import com.PracticaVara.springJwt.repository.ChatroomRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.PracticaVara.springJwt.service.AccountServices.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatroomMessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ChatroomMessageRepository chatroomMessageRepository;
    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    public ChatroomMessageService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public ResponseEntity<Object> getAllMessage(String id) {
        return ResponseEntity.ok().body(chatroomMessageRepository.findByChatroom_id(id));
    }

    public void saveMessage(JsonNode message) {
        String jwt = message.get("token").asText();
        String messageToSend = message.get("message").asText();
        String chatroomID = message.get("chatroomID").asText();
        Optional<User> user = userRepository.findByUsername(jwtService.extractUsername(jwt));
        ChatroomMessage chatroomMessage = new ChatroomMessage();
        chatroomMessage.setSender(user.get().getId());
        chatroomMessage.setChatroom(chatroomRepository.findById(chatroomID).get());
        chatroomMessage.setMessage(messageToSend);
        chatroomMessage.setSendAt(LocalDateTime.now());
        chatroomMessage = chatroomMessageRepository.save(chatroomMessage);
        this.simpMessagingTemplate.convertAndSend("/message", chatroomMessage);
    }
}
