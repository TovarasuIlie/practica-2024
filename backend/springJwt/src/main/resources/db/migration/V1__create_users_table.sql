CREATE TABLE IF NOT EXISTS users (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    email_verified bit(1) DEFAULT b'0',
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    address varchar(255) DEFAULT NULL,
    password varchar(255) NOT NULL,
    ip_address varchar(255) NOT NULL,
    role enum('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER') DEFAULT 'ROLE_USER',
    registered_date datetime(6) DEFAULT NULL
)