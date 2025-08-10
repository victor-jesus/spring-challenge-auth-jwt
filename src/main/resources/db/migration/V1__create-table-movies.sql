CREATE TABLE movies
(
    id                TEXT PRIMARY KEY NOT NULL UNIQUE,
    name              TEXT             NOT NULL,
    duration_in_minutes INT,
    genre             TEXT             NOT NULL,
    director_name      TEXT
);