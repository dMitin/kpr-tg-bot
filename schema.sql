create table tg_user
(
    id         serial not null
        constraint tg_user_pk
            primary key,
    username   text,
    tg_id      integer,
    first_name text,
    last_name  text,
    is_admin   boolean
);

alter table tg_user
    owner to postgres;

create unique index tg_user_tg_id_uindex
    on tg_user (tg_id);

create index tg_user_username_index
    on tg_user (username);

create unique index tg_user_username_uindex
    on tg_user (username);