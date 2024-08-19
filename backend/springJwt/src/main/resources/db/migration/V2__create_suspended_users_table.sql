CREATE TABLE IF NOT EXISTS suspended_accounts (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ending_date datetime(6) DEFAULT NULL,
  ip_address varchar(255) NOT NULL,
  permanent_suspend bit(1) NOT NULL,
  starting_date datetime(6) NOT NULL,
  suspend_reason varchar(255) NOT NULL,
  admin_id int(11) NOT NULL,
  user_id int(11) NOT NULL
)
