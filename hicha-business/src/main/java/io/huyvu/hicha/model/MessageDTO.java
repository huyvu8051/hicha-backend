package io.huyvu.hicha.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {
    Integer conversationId;
    Integer senderId;
    String messageText;
}
