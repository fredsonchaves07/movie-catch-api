--CREATE TABLE  USERS_ROLES
--https://github.com/fredsonchaves07/movie-catch-api/issues/179
CREATE TABLE IF NOT EXISTS users_roles
(
    user_id    varchar REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id    varchar REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id)
);