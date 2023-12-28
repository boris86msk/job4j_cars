insert into engine(name) values ('v4');
insert into engine(name) values ('v6');
insert into engine(name) values ('v8');

INSERT INTO auto_user (login, password) VALUES ('Ivanov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Petrov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Vasin', 'root');
INSERT INTO auto_user (login, password) VALUES ('Sidorov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Avdeev', 'root');
INSERT INTO auto_user (login, password) VALUES ('Stepanov', 'root');

insert into car(brand, model, engine_id, user_id) values ('lada', 'Vesta', 2, 6);
insert into car(brand, model, engine_id, user_id) values ('uaz', 'Patriot', 3, 5);
insert into car(brand, model, engine_id, user_id) values ('Kia', 'Rio', 1, 4);
insert into car(brand, model, engine_id, user_id) values ('VW', 'Golf', 1, 3);
insert into car(brand, model, engine_id, user_id) values ('lada', 'Vesta', 1, 2);

insert into auto_post(description, created, auto_user_id) VALUES ('продам', '20-12-2023', 4);
insert into auto_post(description, created, auto_user_id) VALUES ('продам2', '22-12-2023', 5);
insert into auto_post(description, created, auto_user_id) VALUES ('продам3', '26-12-2023', 6);
insert into auto_post(description, created, auto_user_id) VALUES ('продам4', '27-12-2023', 3);
insert into auto_post(description, created, auto_user_id) VALUES ('продам5', '28-12-2023', 2);

insert into files(path, post_id) values('img/vesta1.jpg', 1);
insert into files(path, post_id) values('img/uazpatriot.jpg', 2);