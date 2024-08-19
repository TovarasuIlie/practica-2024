CREATE TABLE IF NOT EXISTS ip_logs (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ip_address varchar(255) NOT NULL,
  used_from datetime(6) NOT NULL,
  user_id int(11) NOT NULL
)
