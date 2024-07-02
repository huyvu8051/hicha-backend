package io.huyvu.hicha.controller;

import io.huyvu.hicha.mapper.MapperUtils;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repo.MessageRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.time.Instant;
import java.util.List;

import static io.huyvu.hicha.mapper.MapperUtils.from;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @PostMapping
    String sendMessage(@RequestBody MessageDTO dto) {

        Message entity = from(dto)
                .map("nm", "name")
                .map("gender", "sex")
                .map("dateOfBirth", "dob")
                .build();

        entity.setSentAt(Instant.now());
        messageRepository.save(entity);
        return "Success";
    }


    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageDTO {
        Long conversationId;
        Long senderId;
        String messageText;
    }


    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    public static class ConversationDetails {
        String conversationName;
        Long conversationId;
        List<Message> messages;
    }

    @GetMapping("{id}")
    ConversationDetails getConversationDetails(@PathVariable Long id) {
        var messages = messageRepository.findByConversationId(id);
        return ConversationDetails.builder()
                .conversationId(id)
                .conversationName("Conversation " + id)
                .messages(messages)
                .build();
    }
}
