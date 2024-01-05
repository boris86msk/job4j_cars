create table car
(
    id         serial   primary key,
    brand      varchar  not null,
    model      varchar  not null,
    engine_id  int      not null references engine(id)
);