CREATE KEYSPACE IF NOT EXISTS hicha
    WITH replication = {
        'class': 'org.apache.cassandra.locator.NetworkTopologyStrategy',
        'replication_factor': 3
        };
USE hicha;

CREATE TABLE IF NOT EXISTS messages
(
    conversation_id int,
    message_id      uuid,
    sender_id       int,
    message_text    text,
    sent_at         timestamp,
    PRIMARY KEY (conversation_id, sent_at)
) WITH CLUSTERING ORDER BY (sent_at DESC);

INSERT INTO hicha.messages (message_id, conversation_id, sender_id, message_text, sent_at)
VALUES (1, 1, 1001, 'Hello, how are you?', '2024-05-03 08:00:00');

INSERT INTO hicha.messages (message_id, conversation_id, sender_id, message_text, sent_at)
VALUES (2, 1, 1002, 'Im fine, thank you!', '2024-05-03 08:05:00');

INSERT INTO hicha.messages (message_id, conversation_id, sender_id, message_text, sent_at)
VALUES (3, 2, 1001, 'Hey there!', '2024-05-03 08:10:00');
