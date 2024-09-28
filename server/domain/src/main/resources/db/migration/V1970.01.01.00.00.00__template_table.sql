CREATE TABLE IF NOT EXISTS users (
   id             BIGSERIAL PRIMARY KEY,
   username       VARCHAR(128) UNIQUE,
   password       VARCHAR(128),
   is_disabled    BOOLEAN
);

CREATE INDEX IF NOT EXISTS users__username__idx
    ON users (username);

CREATE INDEX IF NOT EXISTS users__disabled_username__idx
    ON users (is_disabled, username);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT,
    role    VARCHAR(32)
);

CREATE UNIQUE INDEX IF NOT EXISTS user_roles__user_role__idx
    ON user_roles (user_id, role);

CREATE TABLE IF NOT EXISTS jwt_tokens (
    jwt_token     VARCHAR(1024) unique,
    expiration_ts BIGINT,
    user_id       BIGINT,
    type          VARCHAR(16),
    revoked       BOOLEAN
);

CREATE INDEX IF NOT EXISTS jwt_tokens__expiration_ts__idx
    ON jwt_tokens (expiration_ts);

CREATE INDEX IF NOT EXISTS jwt_tokens__user_id__idx
    ON jwt_tokens (user_id);

CREATE INDEX IF NOT EXISTS jwt_tokens__token__idx
    ON jwt_tokens (jwt_token);