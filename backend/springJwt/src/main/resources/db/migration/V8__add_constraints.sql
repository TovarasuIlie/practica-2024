ALTER TABLE suspended_accounts
  ADD UNIQUE KEY UK_admin_id (admin_id),
  ADD CONSTRAINT FK_admin_id FOREIGN KEY (admin_id) REFERENCES Users(id);

ALTER TABLE suspended_accounts
  ADD UNIQUE KEY UK_user_id (user_id),
  ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE;

ALTER TABLE announcements
  ADD CONSTRAINT FK_Announcemets_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE;

ALTER TABLE ip_logs
  ADD CONSTRAINT FK_ip_logs_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE;

ALTER TABLE reports
  ADD CONSTRAINT FK_Reports_announcement_id FOREIGN KEY (announcement_id) REFERENCES Announcements(id) ON DELETE CASCADE,
  ADD CONSTRAINT FK_Reports_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE;

ALTER TABLE reset_password_codes
  ADD UNIQUE KEY UK_Reset_password_codes_user_id (user_id),
  ADD CONSTRAINT FK_Reset_password_codes_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE;

ALTER TABLE announcements
    ADD FOREIGN KEY FK_Announcement_category_id (category_id) REFERENCES Categories(id) ON DELETE CASCADE;