--
-- create the database
-- 


drop database if exists cosmos_meta;

create database cosmos_meta;

drop database if exists value_set;

create database value_set;

use cosmos_meta;

drop table if exists current_version;

create table current_version (
	file_prefix varchar(8) unique,
	file_name varchar(256) unique
);

insert into current_version values ('000', '001-create-database.sql');