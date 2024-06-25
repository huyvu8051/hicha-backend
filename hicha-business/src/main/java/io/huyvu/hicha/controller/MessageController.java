package io.huyvu.hicha.controller;

import io.huyvu.hicha.mapper.MapperUtils;
import io.huyvu.hicha.mapper.MessageMapper;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repo.MessageRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import static io.huyvu.hicha.mapper.MapperUtils.map;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;
    String name = "global";
    @PostMapping
    String sendMessage(String test,int abc, @RequestBody MessageDTO dto) {
        var name = "chungta";
        int asd = 1;
        System.out.println(name);
        var entity = map(dto, Message.class);
        entity.setSentAt(Instant.now());
        messageRepository.save(entity);
        return "Success";
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageDTO {
        Integer conversationId;
        Integer senderId;
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
