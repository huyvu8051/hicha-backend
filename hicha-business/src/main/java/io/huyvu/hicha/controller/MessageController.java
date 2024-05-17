package io.huyvu.hicha.controller;

import io.huyvu.hicha.UnknownSourceTargetType;
import io.huyvu.hicha.UnknownSourceTargetType.KnownSourceTargetType;
import io.huyvu.hicha.UnknownSourceTargetType.KnownTargetType;
import io.huyvu.hicha.UnknownSourceTargetType.KnownSourceType;
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

        MessageRepository msg = new UnknownSourceTargetType().from(dto);
        Message msg1 = new UnknownSourceTargetType().from(dto);

        Message msg2 = new KnownTargetType<>(Message.class).from(dto);
        Message msg3 = new KnownTargetType<>(Message.class).map(MessageDTO.class).from(dto);



        Message from2 = new KnownSourceTargetType<>(MessageDTO.class, Message.class).from(dto);


        Message from = new KnownSourceType<>(MessageDTO.class).from(dto);
        MessageRepository from3 = new KnownSourceType<>(MessageDTO.class).from(dto);
        Message from1 = new KnownSourceType<>(MessageDTO.class).to(Message.class).from(dto);


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
