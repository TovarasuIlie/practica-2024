package com.PracticaVara.springJwt.service.ChatRoomServices;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Chatroom.Chatroom;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.ChatroomRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatroomService {

    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> getOrCreateChatroom(Integer announcementID, Integer sellerID, Integer buyerID) {
        Optional<Chatroom> chatroom = chatroomRepository.findByAnnouncement_idAndSeller_idAndBuyer_id(announcementID, sellerID, buyerID);
        if(chatroom.isEmpty() && !sellerID.equals(buyerID)) {
            Chatroom newChatroom = new Chatroom();
            newChatroom.setAnnouncement(announcementRepository.findById(announcementID).get());
            newChatroom.setSeller(userRepository.findById(sellerID).get());
            newChatroom.setBuyer(userRepository.findById(buyerID).get());
            newChatroom.setCreateDate(LocalDateTime.now());
            newChatroom = chatroomRepository.save(newChatroom);
            return ResponseEntity.status(HttpStatus.CREATED).body(newChatroom);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(chatroom.get());
        }
    }

    public ResponseEntity<Object> getMySellChatrooms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        Optional<List<Chatroom>> chatroomList = chatroomRepository.findBySeller_id(user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(chatroomList.get());
    }

    public ResponseEntity<Object> getMyBuyChatrooms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        Optional<List<Chatroom>> chatroomList = chatroomRepository.findByBuyer_id(user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(chatroomList.get());
    }
}
