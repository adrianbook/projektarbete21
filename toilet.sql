create database toiletdb;
use toiletdb;

create table toilet
(Id integer not null,
longitude decimal(9,6) not null,
latitude decimal(9,6) not null,
primary key(Id));

insert into toilet
(Id, longitude, latitude)
values
(1, 57.697905,11.929820);
insert into toilet
(Id, longitude, latitude)
values
(2, 57.699229,11.934269);
insert into toilet
(Id, longitude, latitude)
values
(2, 57.689921,11.949313);