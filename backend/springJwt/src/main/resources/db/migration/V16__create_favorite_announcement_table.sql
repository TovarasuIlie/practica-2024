CREATE TABLE favorite_announcement (
    id int(11) AUTO_INCREMENT PRIMARY KEY,
    user_id int(11) NOT NULL,
    announcement_id int(11) NOT NULL,
    added_to_favorites_date datetime(6) DEFAULT CURRENT_TIMESTAMP
);