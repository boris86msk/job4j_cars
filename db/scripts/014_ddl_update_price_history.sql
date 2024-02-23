ALTER TABLE price_history DROP COLUMN after;
ALTER TABLE price_history DROP COLUMN before;

ALTER TABLE price_history ADD COLUMN price bigint not null;
DROP TABLE participates;
ALTER TABLE auto_post ADD COLUMN status boolean default true;