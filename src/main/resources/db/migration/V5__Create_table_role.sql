--CREATE TABLE ROLES
--https://github.com/fredsonchaves07/movie-catch-api/issues/179
CREATE TABLE IF NOT EXISTS roles
(
    id         varchar primary key,
    name       varchar not null,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
)