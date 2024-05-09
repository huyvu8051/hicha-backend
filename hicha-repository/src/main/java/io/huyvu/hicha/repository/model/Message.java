package io.huyvu.hicha.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    Long messageId;
    Integer conversationId;
    Integer senderId;
    String messageText;
    Instant sentAt;
}
