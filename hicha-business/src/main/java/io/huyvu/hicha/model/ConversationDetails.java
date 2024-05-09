package io.huyvu.hicha.model;

import io.huyvu.hicha.repository.model.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ConversationDetails {
    String conversationName;
    Long conversationId;
    List<Message> messages;
}
