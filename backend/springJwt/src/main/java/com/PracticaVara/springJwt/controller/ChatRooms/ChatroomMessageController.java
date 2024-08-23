package com.PracticaVara.springJwt.controller.ChatRooms;

import com.PracticaVara.springJwt.service.ChatRoomServices.ChatroomMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${spring.originUrl}")
@RestController
public class ChatroomMessageController {

    @Autowired
    private ChatroomMessageService chatroomMessageService;

    @MessageMapping("/send/send-message-to-chatroom")
    public void sendMessage(JsonNode message) {
        chatroomMessageService.saveMessage(message);
    }
}
