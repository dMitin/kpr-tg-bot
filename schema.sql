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



create table tg_warning
(
    id serial NOT NULL PRIMARY KEY,
    warning_time timestamp,
    warned_tg_user serial not null,
    created_by serial not null,
    FOREIGN KEY (warned_tg_user) REFERENCES tg_user (id),
    FOREIGN KEY (created_by) REFERENCES tg_user (id)
);

create unique index tg_warning_id_uindex
    on tg_warning (id);

create table tg_ban
(
    tg_user_id serial NOT NULL PRIMARY KEY,
    ban_time timestamp,
    created_by serial not null,
    FOREIGN KEY (created_by) REFERENCES tg_user (id),
    FOREIGN KEY (tg_user_id) REFERENCES tg_user (id) DEFERRABLE INITIALLY DEFERRED
);