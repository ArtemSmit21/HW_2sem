create table if not exists trip (
    id bigint generated by default as identity,
    name varchar(255),
    voting_room_id bigint,
    primary key (id)
)