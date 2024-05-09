package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.ConversationDetails;
import io.huyvu.hicha.hichabusiness.model.Message;
import io.huyvu.hicha.hichabusiness.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @PostMapping
    void sendMessage(@RequestBody Message message) {
        if(message.getSentAt() == null){
            message.setSentAt(Instant.now());
        }
        messageRepository.save(message);
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
