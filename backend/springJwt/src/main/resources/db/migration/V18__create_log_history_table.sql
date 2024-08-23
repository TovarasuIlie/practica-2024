CREATE TABLE log_history (
                             id int(11) AUTO_INCREMENT PRIMARY KEY,
                             user_id int(11) NOT NULL,
                             action varchar(255) NOT NULL,
                             ip_address varchar(255) NOT NULL,
                             action_date datetime(6) DEFAULT CURRENT_TIMESTAMP
);