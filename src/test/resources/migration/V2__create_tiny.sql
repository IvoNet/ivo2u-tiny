CREATE TABLE tiny (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(4242),
    counter INTEGER DEFAULT 0 NOT NULL,
    creation_date TIMESTAMP
);

