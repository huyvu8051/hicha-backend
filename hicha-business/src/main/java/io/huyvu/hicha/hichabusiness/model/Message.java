package io.huyvu.hicha.hichabusiness.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table("messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @PrimaryKey
    @Column("message_id")
    UUID messageId;
    @Column("conversation_id")
    Integer conversationId;
    @Column("sender_id")
    Integer senderId;
    @Column("message_text")
    String messageText;
    @Column("sent_at")
    Instant sentAt;


}
