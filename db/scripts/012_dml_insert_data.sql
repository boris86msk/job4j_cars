INSERT INTO auto_user (login, password) VALUES ('Ivanov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Petrov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Vasin', 'root');
INSERT INTO auto_user (login, password) VALUES ('Sidorov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Avdeev', 'root');
INSERT INTO auto_user (login, password) VALUES ('Stepanov', 'root');

insert into car(brand, model, body_id) values ('lada', 'Vesta', 1);
insert into car(brand, model, body_id) values ('uaz', 'Patriot', 5);
insert into car(brand, model, body_id) values ('Kia', 'Rio', 2);
insert into car(brand, model, body_id) values ('VW', 'Golf', 2);
insert into car(brand, model, body_id) values ('lada', 'Vesta', 1);

insert into auto_post(description, created, auto_user_id, car_id) VALUES ('продам', '20-12-2023', 4, 1);
insert into auto_post(description, created, auto_user_id, car_id) VALUES ('продам2', '22-12-2023', 5, 3);
insert into auto_post(description, created, auto_user_id, car_id) VALUES ('продам3', '26-12-2023', 6, 5);
insert into auto_post(description, created, auto_user_id, car_id) VALUES ('продам4', '27-12-2023', 3, 2);
insert into auto_post(description, created, auto_user_id, car_id) VALUES ('продам5', '28-12-2023', 2, 4);

insert into files(path, post_id) values('img/vesta1.jpg', 1);
insert into files(path, post_id) values('img/uazpatriot.jpg', 2);