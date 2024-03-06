ALTER TABLE car DROP COLUMN brand;
ALTER TABLE car ADD COLUMN brand_id int not null references car_model(id);