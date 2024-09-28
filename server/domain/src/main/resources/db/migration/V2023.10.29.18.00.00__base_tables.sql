CREATE TABLE IF NOT EXISTS category (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(32),
    description VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS issue (
    id               BIGSERIAL PRIMARY KEY,
    description      text,
    status           VARCHAR(32),
    priority         VARCHAR(32),
    category_id      BIGINT REFERENCES category(id),
    reporter_id      BIGINT REFERENCES users(id),
    assignee_id      BIGINT REFERENCES users(id),
    created_at       BIGINT,
    updated_at       BIGINT,
    document_path    VARCHAR(1024),
    actuality_status VARCHAR(32),
    location_lat     DOUBLE PRECISION,
    location_lon     DOUBLE PRECISION
);

CREATE INDEX IF NOT EXISTS issue_reporter_id_idx ON issue(reporter_id);

CREATE INDEX IF NOT EXISTS issue_assignee_id_idx ON issue(assignee_id);

CREATE INDEX IF NOT EXISTS issue_status_idx ON issue(status);