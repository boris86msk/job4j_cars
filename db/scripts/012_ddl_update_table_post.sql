ALTER TABLE auto_post ADD COLUMN file_id int REFERENCES files(id);
ALTER TABLE auto_post ADD COLUMN price int;

ALTER TABLE files DROP COLUMN post_id;

ALTER TABLE car ADD COLUMN tm varchar;
ALTER TABLE car ADD COLUMN volume varchar;
ALTER TABLE car ADD COLUMN power varchar;
ALTER TABLE car ADD COLUMN drive varchar;
ALTER TABLE car ADD COLUMN age int not null;
ALTER TABLE car ADD COLUMN mileage int not null;
ALTER TABLE car ADD COLUMN fuel varchar not null;
ALTER TABLE car ADD COLUMN color varchar;