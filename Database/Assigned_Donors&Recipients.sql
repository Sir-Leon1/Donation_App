create table `Assigned_Donors&Recipients`
(
    DonorUsername     varchar(127)             not null,
    DonorEmail        varchar(127)             not null,
    Donation          varchar(127)             not null,
    RecipientUsername varchar(127)             not null,
    RecipientEmail    varchar(127)             not null,
    Request           varchar(127)             not null,
    Status            varchar(127) default '-' null
);

