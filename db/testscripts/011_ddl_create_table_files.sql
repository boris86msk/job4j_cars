create table files
(
    id      serial primary key,
    path    varchar,
    post_id int references auto_post(id)
);