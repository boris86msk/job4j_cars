INSERT INTO car(brand, model, body_id, tm, volume, power, drive, age, mileage, fuel, color) values ('Лада','Веста', 3,'МКПП', 1.8, 105,'FWD', 2019, 90000, 'Бензин', 'Серый');
INSERT INTO car(brand, model, body_id, tm, volume, power, drive, age, mileage, fuel, color) values('Лада', 'Нива', 3, 'МКПП', 1.6, 86, '4WD', 2012, 160000, 'Бензин', 'Космос');
insert into participates(user_id, post_id) VALUES (1, 1);
insert into participates(user_id, post_id) VALUES (2, 2);
insert into participates(user_id, post_id) VALUES (3, 3);
insert into participates(user_id, post_id) VALUES (4, 4);
insert into participates(user_id, post_id) VALUES (1, 5);
insert into participates(user_id, post_id) VALUES (5, 6);


create table pasport
(
    id         serial   primary key,
    name       varchar  not null  unique
);
create table human
(
    id         serial   primary key,
    model      varchar  not null,
    p_id   int      not null references pasport(id)
        ON DELETE CASCADE
);
