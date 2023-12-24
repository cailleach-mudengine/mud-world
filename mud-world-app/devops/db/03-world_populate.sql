set role mudengine_world;

delete from mud_place_exit;
delete from mud_place_attr;
delete from mud_place;
delete from mud_place_class_attr;
delete from mud_place_class;

insert into mud_place_class(code, name, default_description) values('PLAIN', 'Plain', 'A plain');
insert into mud_place_class(code, name, default_description) values('FOREST', 'Forest', 'A forest');
insert into mud_place_class(code, name, default_description) values('LAKE', 'Lake', 'A lake');
insert into mud_place_class(code, name, default_description) values('RIVER', 'River', 'The surface');
insert into mud_place_class(code, name, default_description) values('UWATER', 'Lake', 'Under the surface');
insert into mud_place_class(code, name, default_description) values('SWAMP', 'Swamp', 'A swamp');
insert into mud_place_class(code, name, default_description) values('SKY', 'Sky', 'The sky');
insert into mud_place_class(code, name, default_description) values('SPACE', 'Space', 'The empty space');
insert into mud_place_class(code, name, default_description, size_capacity) values('RUIN', 'Ruins', 'Ruins', 0);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code) values('TUNNEL', 'Tunnel', 'Tunnel', 10, null, 'RUIN');
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code) values('BIGTUNNEL', 'Large Tunnel', 'Passagem subterranea', 100, 'TUNNEL', 'TUNNEL');
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('WORKSHOP', 'Workshop', 'Workshop', 90, 'BIGTUNNEL', 'BIGTUNNEL', 500, 100);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('POD', 'Escape Pod', 'Escape Pod', 90, 'PLAIN', 'PLAIN', null, null);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('LNODE', 'Living Node', 'Living Node', 90, 'BIGTUNNEL', 'BIGTUNNEL', 300, 100);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('RSTATION', 'Radio Station', 'Radio Station', 40, 'BIGTUNNEL', 'BIGTUNNEL', 100, 50);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('LRTRANS', 'Long Range Transmitter', 'Long Range Transmitter', 50, 'RSTATION', 'BIGTUNNEL', 100, 50);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('NETWORK', 'Remote Network', 'Remote Network', 50, 'LRTRANS', 'BIGTUNNEL', 1000, 500);

insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('SCOLLECT', 'Scrap Collector', 'Scrap Collector', 80, 'BIGTUNNEL', 'BIGTUNNEL', 100, 50);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('RECYCLER', 'Recycler', 'Recycler', 60, 'SCOLLECT', 'BIGTUNNEL', 300, 100);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('FACTORY', 'Factory', 'Factory', 80, 'BIGTUNNEL', 'BIGTUNNEL', 1000, 500);

insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('FARM', 'Farm', 'Farm', 200, 'BIGTUNNEL', 'BIGTUNNEL', 500, 100);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('FPROCESS', 'Food Processor', 'Food Processor', 100, 'BIGTUNNEL', 'BIGTUNNEL', 500, 100);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('IFPROCESS', 'Improved Food Processor', 'Improved Food Processor', 100, 'FPROCESS', 'BIGTUNNEL', 1000, 500);
insert into mud_place_class(code, name, default_description, size_capacity, parent_class_code, demised_class_code, build_cost, build_effort) values('TCENTER', 'Training Center', 'Training Center', 80, 'BIGTUNNEL', 'BIGTUNNEL', 1000, 500);

insert into mud_place_class_attr(class_code, code, value) values('POD', 'HP', 100);
insert into mud_place_class_attr(class_code, code, value) values('POD', 'MAXHP', 100);

insert into mud_place_class_attr(class_code, code, value) values('LNODE', 'HP', 500);
insert into mud_place_class_attr(class_code, code, value) values('LNODE', 'MAXHP', 500);


INSERT INTO mud_place(code, class_code, description) values(1, 'POD', 'The escape pod that saved you from the doomed spaceship.');
INSERT INTO mud_place(code, class_code, description) values(2, 'PLAIN', 'A plain where your escape pod has landed.');
INSERT INTO mud_place(code, class_code, description) values(3, 'LAKE', 'Surface of the lake.');
INSERT INTO mud_place(code, class_code, description) values(4, 'UWATER', 'Bottom of the lake.');
INSERT INTO mud_place(code, class_code, description) values(5, 'FOREST', 'A thick forest.');
INSERT INTO mud_place(code, class_code) values(6, 'TUNNEL');
INSERT INTO mud_place(code, class_code) values(7, 'BIGTUNNEL');
INSERT INTO mud_place(code, class_code) values(8, 'LNODE');


INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (1, 'OUT', true, true, 2);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (2, 'NORTH', true, true, 3);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (2, 'IN', true, true, 1);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (2, 'EAST', true, true, 5);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (3, 'DOWN', false, true, 4);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (4, 'UP', true, true, 3);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (5, 'DOWN', true, true, 6);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (6, 'DOWN', true, true, 7);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (7, 'IN', true, true, 8);
INSERT INTO mud_place_exit(place_code, direction, opened, visible, target_place_code) values (8, 'OUT', true, true, 7);



select setval('MUD_PLACE_SEQ', 99);

reset role;