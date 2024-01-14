create table history
(
    id         serial   primary key,
    startAt    timestamp,
    endAt      timestamp,
    owner_id   int references owners(id)
);