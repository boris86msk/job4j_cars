ALTER TABLE price_history DROP COLUMN after;
ALTER TABLE price_history DROP COLUMN before;

ALTER TABLE price_history ADD COLUMN price bigint not null;
ALTER TABLE auto_post ADD COLUMN status boolean default true;

ALTER TABLE auto_user ADD COLUMN user_name varchar not null;