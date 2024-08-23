ALTER TABLE favorite_announcement
    ADD CONSTRAINT FK_favorite_announcement_user FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    ADD CONSTRAINT FK_favorite_announcement_announcement FOREIGN KEY (announcement_id) REFERENCES Announcements(id) ON DELETE CASCADE;