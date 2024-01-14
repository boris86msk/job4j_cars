create table files
(
    id      serial primary key,
    path    varchar not null unique,
    post_id int references auto_post(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);