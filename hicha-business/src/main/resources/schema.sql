# use hicha;
create table if not exists user(
    id int primary key auto_increment,
    name varchar(255) not null
);

insert into user(name) values ('Tùng Dương')