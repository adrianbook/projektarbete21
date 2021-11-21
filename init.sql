create database if not exists toiletdb;
use toiletdb;

create table if not exists toilet
(id bigint auto_increment not null,
longitude double not null,
latitude double not null,
primary key(id));

insert into toilet
(longitude, latitude)
values
(57.697905,11.929820);
insert into toilet
(longitude, latitude)
values
(57.699229,11.934269);
insert into toilet
(longitude, latitude)
values
(57.689921,11.949313);