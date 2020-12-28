-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

create table tUsers
(
    userId   bigserial not null primary key,
    name varchar(50),
    address_id bigint
);

create table tAddressDataSet
(
    addressId   bigserial not null primary key,
    street varchar(50)
);

create table tPhoneDataSet
(
    phoneId   bigserial not null primary key,
    phone_number varchar(50),
    user_id bigint
);