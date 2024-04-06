create table Recipient_UD
(
    username    varchar(127)             not null,
    email       varchar(127)             not null,
    phone       varchar(127)             not null,
    location    varchar(127)             not null,
    status      varchar(127) default '-' not null,
    requestType varchar(127) default '-' not null,
    usertype    varchar(127)             not null,
    orgWeb      varchar(127)             null
);

