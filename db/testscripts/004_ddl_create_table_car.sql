create table car
(
    id         serial   primary key,
    brand      varchar  not null,
    model      varchar  not null,
    body_id    int      not null references body_type(id)
);