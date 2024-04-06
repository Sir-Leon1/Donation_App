create table Donor_UD
(
    email        varchar(127)             not null,
    username     varchar(127)             not null,
    location     varchar(127)             not null,
    userType     varchar(127)             not null,
    phoneno      varchar(127)             not null,
    status       varchar(127) default '-' not null,
    donationType varchar(127) default '-' not null
);

