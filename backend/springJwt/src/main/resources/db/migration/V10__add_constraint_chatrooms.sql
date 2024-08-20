ALTER TABLE chatrooms
  ADD CONSTRAINT FK_Chatrooms_announcement_id FOREIGN KEY (announcement_id) REFERENCES Announcements(id) ON DELETE CASCADE,
  ADD CONSTRAINT FK_Chatrooms_seller_id FOREIGN KEY (seller_id) REFERENCES Users(id) ON DELETE CASCADE,
  ADD CONSTRAINT FK_Chatrooms_buyer_id FOREIGN KEY (buyer_id) REFERENCES Users(id) ON DELETE CASCADE;