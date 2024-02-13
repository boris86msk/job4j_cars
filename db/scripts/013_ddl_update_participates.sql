ALTER TABLE participates DROP CONSTRAINT participates_user_id_post_id_key;
CREATE UNIQUE INDEX unique_participates_user_post ON participates (user_id, post_id);

