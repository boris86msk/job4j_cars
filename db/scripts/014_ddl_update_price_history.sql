ALTER TABLE price_history DROP COLUMN after;
ALTER TABLE price_history DROP COLUMN before;

ALTER TABLE price_history ADD COLUMN price bigint not null;
ALTER TABLE auto_post ADD COLUMN status boolean default true;

ALTER TABLE auto_user ADD COLUMN user_name varchar not null;

insert into owners(name, user_id) VALUES('Волков Олег Александрович', 6);
insert into owners(name, user_id) VALUES('Туманов Иван Арсентьевич', 7);
insert into owners(name, user_id) VALUES('Мельников Руслан Даниилович', 10);

insert into participates(user_id, post_id) VALUES();


insert into history_owners(car_id, owner_id) VALUES(6, 10);

insert into history(startat, endat, owner_id) VALUES('10-07-2019', '07-02-2022', 10);
insert into history(startat, owner_id) VALUES('02-12-2022', 11);