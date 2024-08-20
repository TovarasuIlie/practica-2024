ALTER TABLE chatroom_messages
   ADD FOREIGN KEY FK_Chatroom_messages_chatroom_id (chatroom_id) REFERENCES Chatrooms(id) ON DELETE CASCADE,
   ADD FOREIGN KEY FK_Chatroom_messages_sender_id (sender_id) REFERENCES Users(id) ON DELETE CASCADE;