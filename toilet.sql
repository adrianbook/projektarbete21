create database toiletdb;
use toiletdb;

create table toilet
(id bigint not null,
longitude double not null,
latitude double not null,
primary key(id));

insert into toilet
(id, longitude, latitude)
values
(1, 57.697905,11.929820);
insert into toilet
(id, longitude, latitude)
values
(2, 57.699229,11.934269);
insert into toilet
(id, longitude, latitude)
values
(3, 57.689921,11.949313);