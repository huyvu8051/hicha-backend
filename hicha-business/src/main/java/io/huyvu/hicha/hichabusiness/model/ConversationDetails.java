package io.huyvu.hicha.hichabusiness.model;

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
