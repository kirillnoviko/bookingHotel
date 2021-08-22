create table if not exists users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    name varchar(200) not null,
    surname varchar(200) not null,
    gmail varchar(200) not null,
    password varchar(50) not null,
    is_deleted boolean default false not null,
    is_banned boolean default false not null,
    created timestamp with time zone not null,
    changed timestamp with time zone not null,
    rating_average bigint,
    birth_date timestamp with time zone
);

alter table users owner to postgres;

create unique index if not exists users_gmail_uindex
    on users (gmail);

create table if not exists rooms
(
    id bigserial not null
        constraint section_hotel_room_pkey
            primary key,
    name varchar(200) not null,
    price bigint not null,
    principle_of_placement varchar(200) not null,
    comfort_level varchar(200) not null,
    number_room bigint not null,
    rating_average bigint not null,
    is_deleted boolean default false,
    constraint section_hotel_room_name_price_principle_of_placement_comfor_key
        unique (name, price, principle_of_placement, comfort_level)
);

alter table rooms owner to postgres;

create table if not exists comforts
(
    id bigserial not null
        constraint comfort_pkey
            primary key,
    name_comfort varchar(100) not null,
    constraint comfort_name_additional_id_key
        unique (name_comfort, id)
);

alter table comforts owner to postgres;

create table if not exists comforts_rooms
(
    id bigserial not null
        constraint additional_in_section_pkey
            primary key,
    id_room bigint not null
        constraint additional_in_section_id_section_hotel_room_fkey
            references rooms,
    id_comfort bigint not null
        constraint additional_in_section_id_additional_comfort_fkey
            references comforts,
    constraint additional_in_section_id_section_hotel_room_id_additional_c_key
        unique (id_room, id_comfort)
);

alter table comforts_rooms owner to postgres;

create table if not exists orders
(
    id bigserial not null
        constraint orders_pkey
            primary key,
    id_room bigint not null
        constraint orders_id_room_fkey
            references rooms,
    data_check_in timestamp with time zone not null,
    data_check_out timestamp with time zone not null,
    status varchar(50) not null,
    id_user bigint not null
        constraint orders_id_user_fkey
            references users,
    created timestamp with time zone not null,
    changed timestamp with time zone not null,
    rating_for_room bigint,
    rating_for_client bigint,
    constraint orders_created_changed_key
        unique (created, changed)
);

alter table orders owner to postgres;

create table if not exists roles
(
    id bigserial not null
        constraint system_pkey
            primary key,
    role_name varchar(100) not null
);

alter table roles owner to postgres;

create table if not exists user_roles
(
    id bigserial not null
        constraint user_roles_pkey
            primary key,
    user_id bigint not null
        constraint user_roles_user_id_fkey
            references users,
    role_id bigint not null
        constraint user_roles_role_id_fkey
            references roles
);

alter table user_roles owner to postgres;

create table if not exists uuid_key_registration
(
    id serial not null
        constraint table_name_pk
            primary key,
    uuid varchar,
    created timestamp not null,
    id_user bigint not null
        constraint uuid_key_registration_users_id_fk
            references users
);

alter table uuid_key_registration owner to postgres;

create unique index if not exists table_name_id_uindex
    on uuid_key_registration (id);

