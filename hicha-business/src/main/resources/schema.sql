# use hicha;
create table if not exists users
(
    user_id  bigint primary key auto_increment,
    username varchar(255) not null
);

create table if not exists conversations
(
    conversation_id bigint auto_increment primary key
);

create table if not exists user_conversations
(
    user_id         bigint,
    conversation_id bigint,
    primary key (user_id, conversation_id),
    foreign key (user_id) references users (user_id),
    foreign key (conversation_id) references conversations (conversation_id)
);

create table if not exists messages
(
    message_id      bigint auto_increment primary key,
    conversation_id bigint,
    sender_id       bigint,
    message_text    text,
    sent_at         timestamp default current_timestamp,
    foreign key (conversation_id) references conversations (conversation_id),
    foreign key (sender_id) references users (user_id)
);


