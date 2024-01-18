create table history_owners
(
    id         serial   primary key,
    car_id     int not null REFERENCES car(id),
    owner_id   int not null REFERENCES owners(id),
    UNIQUE     (car_id, owner_id)
);