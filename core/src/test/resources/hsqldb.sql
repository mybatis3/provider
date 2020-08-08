-- SET DATABASE TRANSACTION CONTROL MVCC;

drop table country if exists;

create table country (
  id integer,
  countryname varchar(32),
  countrycode varchar(2)
);

insert into country (id, countryname, countrycode) values(1,'China','CN');
insert into country (id, countryname, countrycode) values(2,'France','FR');
insert into country (id, countryname, countrycode) values(3,'Russia','RU');
insert into country (id, countryname, countrycode) values(4,'United Kiongdom','GB');
insert into country (id, countryname, countrycode) values(5,'United States of America','US');