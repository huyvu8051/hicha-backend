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

CREATE TABLE IF NOT EXISTS messages
(
    message_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT,
    sender_id       BIGINT,
    message_text    TEXT,
    sent_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversations (conversation_id),
    FOREIGN KEY (sender_id) REFERENCES users (user_id),
    INDEX idx_message_id (message_id DESC)
);

