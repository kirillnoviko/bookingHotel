create table users
(
    id             bigserial                not null
        constraint users_pkey
            primary key,
    name           varchar(200)             not null,
    surname        varchar(200)             not null,
    gmail          varchar(200)             not null,
    password       varchar(50)              not null,
    is_deleted     boolean                  not null,
    is_banned      boolean                  not null,
    created        timestamp with time zone not null,
    changed        timestamp with time zone not null,
    rating_average bigint,
    birth_date     timestamp with time zone
);

alter table users
    owner to postgres;

create table rooms
(
    id                     bigint  not null
        constraint section_hotel_room_pkey
            primary key,
    name                   varchar(200)                                     not null,
    price                  bigint                                           not null,
    principle_of_placement varchar(200)                                     not null,
    comfort_level          varchar(200)                                     not null,
    number_room            bigint                                           not null,
    rating_average         bigint                                           not null,
    constraint section_hotel_room_name_price_principle_of_placement_comfor_key
        unique (name, price, principle_of_placement, comfort_level)
);

alter table rooms
    owner to postgres;

create table comforts
(
    id           bigint not null
        constraint comfort_pkey
            primary key,
    name_comfort varchar(100)                                        not null,
    constraint comfort_name_additional_id_key
        unique (name_comfort, id)
);

alter table comforts
    owner to postgres;

create table comforts_rooms
(
    id         bigint  not null
        constraint additional_in_section_pkey
            primary key,
    id_room    bigint                                                    not null
        constraint additional_in_section_id_section_hotel_room_fkey
            references rooms,
    id_comfort bigint                                                    not null
        constraint additional_in_section_id_additional_comfort_fkey
            references comforts,
    constraint additional_in_section_id_section_hotel_room_id_additional_c_key
        unique (id_room, id_comfort)
);

alter table comforts_rooms
    owner to postgres;

create table orders
(
    id                bigserial                not null
        constraint orders_pkey
            primary key,
    id_room           bigint                   not null
        constraint orders_id_room_fkey
            references rooms,
    data_check_in     timestamp with time zone not null,
    data_check_out    timestamp with time zone not null,
    status            varchar(50)              not null,
    id_user           bigint                   not null
        constraint orders_id_user_fkey
            references users,
    created           timestamp with time zone not null,
    changed           timestamp with time zone not null,
    general_price     bigint                   not null,
    rating_for_room   bigint,
    rating_for_client bigint,
    constraint orders_id_id_hotel_room_data_check_in_data_check_out_statu_key
        unique (id, id_room, data_check_in, data_check_out, status, id_user, general_price),
    constraint orders_created_changed_key
        unique (created, changed)
);

alter table orders
    owner to postgres;

create table roles
(
    id        bigint  not null
        constraint system_pkey
            primary key,
    role_name varchar(100)                                     not null
);

alter table roles
    owner to postgres;

create table sales
(
    id           bigserial    not null
        constraint sales_pkey
            primary key,
    name_sale    varchar(100) not null,
    percent_sale bigint       not null,
    constraint sales_id_name_percent_sale_key
        unique (id, name_sale, percent_sale)
);

alter table sales
    owner to postgres;

create table user_roles
(
    id      bigint not null
        constraint user_roles_pkey
            primary key,
    user_id bigint                                                not null
        constraint user_roles_user_id_fkey
            references users,
    role_id bigint                                                not null
        constraint user_roles_role_id_fkey
            references roles
);

alter table user_roles
    owner to postgres;

create table sales_orders
(
    id       bigint not null
        constraint sales_orders_pkey
            primary key,
    id_order bigint                                                  not null
        constraint sales_orders_id_order_fkey
            references orders,
    id_sale  bigint                                                  not null
        constraint sales_orders_id_sale_fkey
            references sales
);

alter table sales_orders
    owner to postgres;

