create table Client
(
    id serial not null unique primary key,
    name varchar(50),
    age integer,
    testField varchar(50)

);

create table Account
(
    no varchar(20) not null unique primary key,
    type varchar(50),
    rest float

);