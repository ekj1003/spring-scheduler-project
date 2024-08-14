create table schedule
(
    id       bigint       not null auto_increment,
    contents varchar(500) not null,
    manager varchar(255) not null,
    password varchar(200) not null,
    createTime timestamp not null,
    modifiedTime timestamp not null,
    primary key (id)
);