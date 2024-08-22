CREATE TABLE IF NOT EXISTS announcements (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  content text NOT NULL,
  category_id int(11) NOT NULL,
  created_date datetime(6) NOT NULL,
  expiration_date datetime(6) NOT NULL,
  image_url varchar(255) NOT NULL,
  is_deactivated bit(1) NOT NULL,
  photo_number int(11) NOT NULL,
  price double NOT NULL,
  title varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  user_id int(11) NOT NULL,
  contact_person_name varchar(255) NOT NULL,
  phone_number varchar(10) NOT NULL,
  address varchar(255) NOT NULL,
  currency varchar(255) NOT NULL,
  is_approved bit(1) DEFAULT NULL
)
