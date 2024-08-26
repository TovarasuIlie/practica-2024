ALTER TABLE log_history
    ADD CONSTRAINT FK_log_history_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;