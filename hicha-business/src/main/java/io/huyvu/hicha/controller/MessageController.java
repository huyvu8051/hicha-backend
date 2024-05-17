package io.huyvu.hicha.controller;

import io.huyvu.hicha.MapperUtils;
import io.huyvu.hicha.mapper.MessageMapper;
import io.huyvu.hicha.model.ConversationDetails;
import io.huyvu.hicha.model.MessageDTO;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;


    @PostMapping
    String sendMessage(@RequestBody MessageDTO dto) {
        Message entity = MessageMapper.INSTANCE.map(dto);

        Message o = MapperUtils.mapTo(dto);

        entity.setSentAt(Instant.now());
        messageRepository.save(entity);
        return "Success";
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
