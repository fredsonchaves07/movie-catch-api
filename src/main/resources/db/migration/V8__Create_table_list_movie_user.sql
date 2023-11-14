--CREATE TABLE  USERS_ROLES
--https://github.com/fredsonchaves07/movie-catch-api/issues/227
CREATE TABLE IF NOT EXISTS list_movie_user
(
    id               varchar primary key,
    name             varchar(255),
    description      varchar(255),
    is_private       boolean DEFAULT false,
    movies_series    jsonb,
    created_at       TIMESTAMP DEFAULT now(),
    updated_at       TIMESTAMP DEFAULT now()
);