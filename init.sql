
-- step 1 create database
CREATE DATABASE `tuchong`;

USE tuchong;

-- step 2 create tables
create table role
(
    id        int auto_increment
        primary key,
    role_name varchar(20) not null
);


create table user
(
    id    int auto_increment
        primary key,
    pwd   varchar(64)                  null,
    phone varchar(15) default '-1'     null,
    name  varchar(20) default 'asdf34' not null
);

create index user_phone_index
    on user (phone);


create table user_ope_record
(
    id      int auto_increment
        primary key,
    user_id int                                not null,
    action  varchar(15)                        not null,
    time    datetime default CURRENT_TIMESTAMP not null
);


create table user_role
(
    id      int auto_increment
        primary key,
    user_id int null,
    role_id int null
);

create index user_role__roleindex
    on user_role (role_id);

create index user_role__userindex
    on user_role (user_id);

-- step 3 init some data

INSERT INTO role(`role_name`) VALUES ('normal');
INSERT INTO role(`role_name`) VALUES ('admin');
INSERT INTO user(`pwd`, `phone`, `name`) VALUES ('$2a$10$pjHyw9MSGC/i6k546Ii/0uLFgTK4WYB4.8bSRq7yB4dy.ZpBLxOha', '123456', 'cy');
INSERT INTO user_role(`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO user_role(`user_id`, `role_id`) VALUES (1, 2);