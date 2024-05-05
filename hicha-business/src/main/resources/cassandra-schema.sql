CREATE KEYSPACE IF NOT EXISTS hicha
    WITH replication = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
        };
USE hicha;
CREATE TABLE IF NOT EXISTS messages (
                                        message_id      int,
                                        conversation_id int,
                                        sender_id       int,
                                        message_text    text,
                                        sent_at         timestamp,
                                        PRIMARY KEY (message_id)
);
