ALTER TABLE auto_post ADD COLUMN file_id int REFERENCES files(id);
ALTER TABLE files DROP COLUMN post_id;