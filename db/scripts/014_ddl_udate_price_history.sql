ALTER TABLE price_history DROP COLUMN after;
ALTER TABLE price_history DROP COLUMN before;

ALTER TABLE price_history ADD COLUMN price bigint not null;