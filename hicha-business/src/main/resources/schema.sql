# use hicha;
create table if not exists users
(
    user_id   int primary key auto_increment,
    username varchar(255) not null
);

create table if not exists  conversations
(
    conversation_id int auto_increment primary key
);

create table if not exists  user_conversations
(
    user_id         int,
    conversation_id int,
    primary key (user_id, conversation_id),
    foreign key (user_id) references users(user_id),
    foreign key (conversation_id) references conversations (conversation_id)
);

create table if not exists  messages
(
    message_id      int auto_increment primary key,
    conversation_id int,
    sender_id       int,
    message_text    text,
    sent_at         timestamp default current_timestamp,
    foreign key (conversation_id) references conversations (conversation_id),
    foreign key (sender_id) references users (user_id)
);

insert into users(username) value('huyvu'), ('admin'), ('testuser');
INSERT INTO conversations () value ();
# INSERT INTO user_conversations (user_id, conversation_id) VALUES (1, 1);
INSERT INTO messages (conversation_id, sender_id, message_text) VALUES (1, 1, 'This is a test message.');

