ALTER TABLE suspended_accounts
  ADD UNIQUE KEY UK_admin_id (admin_id);

ALTER TABLE suspended_accounts
  ADD UNIQUE KEY UK_user_id (user_id);

ALTER TABLE announcements
  ADD KEY FK_Announcemets_user_id (user_id);

ALTER TABLE ip_logs
  ADD KEY FK_ip_logs_user_id (user_id);

ALTER TABLE reports
  ADD KEY FK_Reports_announcement_id (announcement_id),
  ADD KEY FK_Reports_user_id (user_id);

ALTER TABLE reset_password_codes
  ADD UNIQUE KEY UK_Reset_password_codes_user_id (user_id);