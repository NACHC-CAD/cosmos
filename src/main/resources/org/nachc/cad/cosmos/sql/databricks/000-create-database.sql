--
-- update metadata table
-- 

create database cosmos_meta;

use cosmos_meta;

drop table if exists current_version;

create table current_version (
	file_prefix varchar(8),
	file_name varchar(256)
);

insert into current_version values ('000', '000-create-database.sql');

