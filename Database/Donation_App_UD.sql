create table Donation_App_UD
(
    ID          int auto_increment
        primary key,
    Email       varchar(127)         not null,
    Username    varchar(127)         not null,
    Password    varchar(127)         not null,
    Location    varchar(127)         not null,
    UserType    varchar(127)         not null,
    PhoneNO     varchar(127)         not null,
    Org_Website varchar(127)         not null,
    Verified    tinyint(1) default 0 not null
);

