# use hicha;
create table if not exists user(
    id int primary key,
    name varchar(255) not null
);

insert into user values (21, 'Tùng Dương')