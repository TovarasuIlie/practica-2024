CREATE TABLE reset_password_codes (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code varchar(255) DEFAULT NULL,
  user_id int(11) DEFAULT NULL
)
