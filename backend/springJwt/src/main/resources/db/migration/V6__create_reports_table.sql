CREATE TABLE reports (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  is_solved bit(1) NOT NULL,
  message varchar(255) NOT NULL,
  announcement_id int(11) NOT NULL,
  user_id int(11) NOT NULL
)
