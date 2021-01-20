-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

create table tUsers
(
    userId   bigserial not null primary key,
    name varchar(50),
    passwordHash integer
);

create table tAdministrators
(
    userId   bigserial not null primary key,
    name varchar(50),
    passwordHash integer
);