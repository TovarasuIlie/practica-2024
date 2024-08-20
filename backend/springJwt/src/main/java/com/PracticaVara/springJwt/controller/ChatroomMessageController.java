package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.service.ChatroomMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatroomMessageController {

    @Autowired
    private ChatroomMessageService chatroomMessageService;

    @MessageMapping("/send/send-message-to-chatroom")
    public void sendMessage(JsonNode message) {
        chatroomMessageService.saveMessage(message);
    }
}
