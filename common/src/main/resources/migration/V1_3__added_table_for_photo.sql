create table user_photos
(
    id      bigserial    not null
        constraint user_photos_pk
            primary key,
    link    varchar(100) not null,
    user_id bigint       not null
        constraint user_photos_users_id_fk
            references users
);

create index user_photos_link_index
    on user_photos (link);

create unique index user_photos_link_uindex
    on user_photos (link);