create table if not exists outbox
(
    id   bigserial PRIMARY KEY,
    data text NOT NULL
)