CREATE TABLE IF NOT EXISTS issue_voice_description (
    id         BIGSERIAL PRIMARY KEY,
    document_path    VARCHAR(1024),
    content_type     VARCHAR(64)
);

ALTER TABLE issue ADD COLUMN is_description_by_voice BOOLEAN DEFAULT FALSE;

ALTER TABLE issue ADD COLUMN voice_description_id BIGINT REFERENCES issue_voice_description(id);