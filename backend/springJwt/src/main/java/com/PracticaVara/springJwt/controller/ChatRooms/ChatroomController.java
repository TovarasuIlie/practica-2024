package com.PracticaVara.springJwt.controller.ChatRooms;

import com.PracticaVara.springJwt.service.ChatRoomServices.ChatroomMessageService;
import com.PracticaVara.springJwt.service.ChatRoomServices.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${spring.originUrl}")
@RequestMapping("api/Chatrooms")
@RestController
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;
    @Autowired
    private ChatroomMessageService chatroomMessageService;

    @GetMapping("/get-or-create-chatroom")
    public ResponseEntity<Object> getOrCreateChatroom(@RequestParam("announcementID") Integer announcementID, @RequestParam("sellerID") Integer sellerID, @RequestParam("buyerID") Integer buyerID) {
        return chatroomService.getOrCreateChatroom(announcementID, sellerID, buyerID);
    }

    @GetMapping("/get-my-sell-chatrooms")
    public ResponseEntity<Object> getMySellerChatrooms() {
        return chatroomService.getMySellChatrooms();
    }

    @GetMapping("/get-my-buy-chatrooms")
    public ResponseEntity<Object> getMyBuyerChatrooms() {
        return chatroomService.getMyBuyChatrooms();
    }

    @GetMapping("/get-all-messages-from-chatroom/{id}")
    public ResponseEntity<Object> getAllmessageFromChatroom(@PathVariable("id") String id) {
        return chatroomMessageService.getAllMessage(id);
    }

//    @PostMapping("/send-message-to-chatroom/{id}")
//    public ResponseEntity<Object> sendMessageToChatroom(@PathVariable("id") String id, @RequestBody String message) {
//        return chatroomMessageService.sendMessage(id, message);
//    }

//    @MessageMapping("/send-message-to-chatroom")
//    public void sendMessage(JsonNode message) {
//        chatroomMessageService.saveMessage(message);
//    }
}
