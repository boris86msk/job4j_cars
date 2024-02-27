ALTER TABLE price_history DROP COLUMN after;
ALTER TABLE price_history DROP COLUMN before;

ALTER TABLE price_history ADD COLUMN price bigint not null;
ALTER TABLE auto_post ADD COLUMN status boolean default true;

ALTER TABLE auto_user ADD COLUMN user_name varchar not null;

insert into owners(name, user_id) VALUES('Петров Иван Аркадиевич', 1);
insert into owners(name, user_id) VALUES('Кравцов Петр Петрович', 4);
insert into owners(name, user_id) VALUES('Колесников Виктор Петрович', 5);


insert into history_owners(car_id, owner_id) VALUES(3, 7);

insert into history(startat, endat, owner_id) VALUES('01-01-2022', '01-01-2012', 6);
insert into history(startat, owner_id) VALUES('01-05-2022', 7);