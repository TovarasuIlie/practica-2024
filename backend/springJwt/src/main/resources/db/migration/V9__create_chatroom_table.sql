CREATE TABLE IF NOT EXISTS chatrooms (
    id varchar(255) PRIMARY KEY NOT NULL,
    announcement_id int(11) NOT NULL,
    seller_id int(11) NOT NULL,
    buyer_id int(11) NOT NULL,
    create_date datetime(6) NOT NULL
)