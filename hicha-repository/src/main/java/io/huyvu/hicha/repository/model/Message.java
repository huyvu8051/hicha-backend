package io.huyvu.hicha.repository.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    Long messageId;
    long conversationId;
    Long senderId;
    String messageText;
    Instant sentAt;
}
