CREATE TABLE IF NOT EXISTS issue_document (
    id         BIGSERIAL PRIMARY KEY,
    document_path    VARCHAR(1024),
    content_type     VARCHAR(64)
);

ALTER TABLE issue ADD COLUMN document_id BIGINT REFERENCES issue_document(id);

ALTER TABLE issue DROP COLUMN document_path;

ALTER TABLE issue DROP COLUMN content_type;