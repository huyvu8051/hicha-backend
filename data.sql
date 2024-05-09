insert into users(username) value ('huyvu'), ('admin'), ('testuser');
insert into conversations () value ();
INSERT INTO user_conversations (user_id, conversation_id) VALUES ((select users.user_id from users where username = 'huyvu' limit 1), (select conversation_id from conversations limit 1));
insert into messages (conversation_id, sender_id, message_text)
values (1, 1, 'This is a test message.');