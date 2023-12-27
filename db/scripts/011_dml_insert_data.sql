INSERT INTO auto_user (login, password) VALUES ('Ivanov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Petrov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Sidorov', 'root');

insert into engine(name) values ('v4');
insert into engine(name) values ('v6');
insert into engine(name) values ('v8');

insert into car(name, engine_id) values ('lada', 2);
insert into car(name, engine_id) values ('uaz', 3);
insert into car(name, engine_id) values ('oka', 1);

insert into auto_post(description, created, auto_user_id) VALUES ('продам', '20-12-2023', 1);
insert into auto_post(description, created, auto_user_id) VALUES ('продам2', '22-12-2023', 1);
insert into auto_post(description, created, auto_user_id) VALUES ('продам3', '26-12-2023', 3);