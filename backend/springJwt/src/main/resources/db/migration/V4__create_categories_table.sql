CREATE TABLE IF NOT EXISTS categories (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  icon_url varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  search_link varchar(255) NOT NULL
)