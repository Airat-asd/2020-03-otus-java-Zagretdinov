create table client
(
    id   integer not null primary key,
    name varchar(50),
    age integer

);

create table account
(
    no   varchar(20) not null primary key,
    type varchar(50),
    rest double

);