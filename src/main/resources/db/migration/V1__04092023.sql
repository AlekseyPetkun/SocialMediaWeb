CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    email      VARCHAR(64)   NOT NULL UNIQUE,
    user_role  VARCHAR(32)   NOT NULL, --enum Role
    first_name VARCHAR(64)   NOT NULL,
    last_name  VARCHAR(64)   NOT NULL,
    enabled    BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE posts
(
    id             SERIAL PRIMARY KEY,
    author_id      INT REFERENCES users (id) NOT NULL,
    title          VARCHAR(1024)             NOT NULL,
    content        TEXT                      NOT NULL,
    image          TEXT,
    date_time_post TIMESTAMP
);

CREATE TABLE subscribers
(
    id           SERIAL PRIMARY KEY,
    from_user_id INT REFERENCES users (id) NOT NULL,
    to_user_id   INT REFERENCES users (id) NOT NULL,
    status       VARCHAR(32)               NOT NULL
);

CREATE TABLE messages
(
    id                SERIAL PRIMARY KEY,
    from_user_id      INT REFERENCES users (id) NOT NULL,
    to_user_id        INT REFERENCES users (id) NOT NULL,
    message           TEXT                      NOT NULL,
    date_time_message TIMESTAMP
);