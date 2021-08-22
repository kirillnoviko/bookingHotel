

INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (16, 'kirill1', '1', 'kiril.1@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);
INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (17, 'kirill2', '2', 'kiril.2@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);
INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (18, 'kirill3', '3', 'kiril.3@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);
INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (19, 'kirill4', '4', 'kiril.4@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);
INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (20, 'kirill5', '5', 'kiril.5@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);
INSERT INTO public.users (id, name, surname, gmail, password, is_deleted, is_banned, created, changed, rating_average, birth_date) VALUES (21, 'kirill6', '6', 'kiril.6@gmail.com', '12345', false, false, '2021-08-22 18:48:15.572000', '2021-08-22 18:48:15.572000', null, null);



INSERT INTO public.roles (id, role_name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO public.roles (id, role_name) VALUES (1, 'ROLE_USER');

INSERT INTO public.rooms (id, name, price, principle_of_placement, comfort_level, number_room, rating_average, is_deleted) VALUES (4, 'apartments', 123, 'single', 'standard', 12, 2, false);
INSERT INTO public.rooms (id, name, price, principle_of_placement, comfort_level, number_room, rating_average, is_deleted) VALUES (1, 'room', 123, 'single', 'lux', 13, 2, false);
INSERT INTO public.rooms (id, name, price, principle_of_placement, comfort_level, number_room, rating_average, is_deleted) VALUES (2, 'familyLarge', 122, 'triple', 'standard', 15, 5, false);
INSERT INTO public.rooms (id, name, price, principle_of_placement, comfort_level, number_room, rating_average, is_deleted) VALUES (3, 'familyLux', 120, 'double', 'lux', 14, 2, false);



INSERT INTO public.comforts (id, name_comfort) VALUES (2, 'wi-fi');
INSERT INTO public.comforts (id, name_comfort) VALUES (1, 'bed');
INSERT INTO public.comforts (id, name_comfort) VALUES (3, 'condition');
INSERT INTO public.comforts (id, name_comfort) VALUES (4, 'TV');
INSERT INTO public.comforts (id, name_comfort) VALUES (5, 'balcony');


INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (8, 1, 1);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (9, 2, 1);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (10, 3, 1);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (11, 4, 1);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (12, 1, 3);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (13, 1, 2);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (14, 2, 3);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (15, 2, 2);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (16, 2, 5);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (17, 3, 4);
INSERT INTO public.comforts_rooms (id, id_room, id_comfort) VALUES (18, 2, 4);


INSERT INTO public.orders (id, id_room, data_check_in, data_check_out, status, id_user, created, changed, rating_for_room, rating_for_client) VALUES (35, 1, '2021-08-22 23:12:22.493000', '2021-08-24 23:12:25.005000', 'BOOKED', 16, '2021-08-22 23:12:48.351000', '2021-08-22 23:12:50.756000', null, null);
INSERT INTO public.orders (id, id_room, data_check_in, data_check_out, status, id_user, created, changed, rating_for_room, rating_for_client) VALUES (36, 1, '2021-07-22 23:12:22.493000', '2021-07-22 23:12:25.005000', 'BOOKED', 16, '2021-08-22 23:12:48.351000', '2021-08-22 23:15:50.756000', null, null);
INSERT INTO public.orders (id, id_room, data_check_in, data_check_out, status, id_user, created, changed, rating_for_room, rating_for_client) VALUES (37, 1, '2021-07-24 23:12:22.493000', '2021-07-27 23:12:25.005000', 'CANCELED', 16, '2021-08-22 23:12:48.351000', '2021-08-22 23:10:50.756000', null, null);
INSERT INTO public.orders (id, id_room, data_check_in, data_check_out, status, id_user, created, changed, rating_for_room, rating_for_client) VALUES (38, 2, '2021-08-22 23:12:22.493000', '2021-08-25 23:12:25.005000', 'BOOKED', 16, '2021-08-22 23:12:48.351000', '2021-08-22 23:19:50.756000', null, null);
INSERT INTO public.orders (id, id_room, data_check_in, data_check_out, status, id_user, created, changed, rating_for_room, rating_for_client) VALUES (39, 3, '2021-08-22 23:12:22.493000', '2021-08-22 23:12:25.005000', 'BOOKED', 16, '2021-08-22 23:12:48.351000', '2021-08-22 22:11:50.756000', null, null);






INSERT INTO public.user_roles (id, user_id, role_id) VALUES (32, 16, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (33, 17, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (34, 18, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (35, 19, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (36, 20, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (37, 21, 1);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (38, 19, 2);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (39, 18, 2);
INSERT INTO public.user_roles (id, user_id, role_id) VALUES (40, 17, 2);


