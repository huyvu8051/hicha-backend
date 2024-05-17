package io.huyvu.hicha.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    Integer conversationId;
    Integer senderId;
    String messageText;

}
