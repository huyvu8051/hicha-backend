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

import static io.huyvu.hicha.MapperUtils.*;


@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;


    @PostMapping
    String sendMessage(@RequestBody MessageDTO dto) {
        Message entity = MessageMapper.INSTANCE.map(dto);


        Message from1 = from(dto);
        Message from2 = to(Message.class).from(dto);
        Message from3 = map(MessageDTO.class).from(dto);
        Message from4 = map(MessageDTO.class).to(Message.class).from(dto);


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
