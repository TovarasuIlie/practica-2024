CREATE TABLE IF NOT EXISTS chatroom_messages (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    chatroom_id varchar(255) NOT NULL,
    sender_id int(11) NOT NULL,
    message text NOT NULL,
    send_at datetime(6) NOT NULL
)