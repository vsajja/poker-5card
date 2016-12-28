CREATE TABLE card
(
    card_id INTEGER DEFAULT nextval('card_card_id_seq'::regclass) NOT NULL,
    name VARCHAR,
    rank INTEGER,
    rank_str VARCHAR,
    suit VARCHAR,
    image_src VARCHAR
);