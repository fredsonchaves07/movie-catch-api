CREATE TABLE IF NOT EXISTS users
(
    id         varchar primary key,
    email      varchar(255),
    is_confirm boolean,
    name       varchar(255) NOT NULL,
    password   varchar(255) NOT NULL
)