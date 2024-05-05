package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.Message;
import io.huyvu.hicha.hichabusiness.repository.MessageCassandraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageCassandraRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessages() {
        var result = new ArrayList<Message>();
        Iterable<Message> all = messageRepository.findAll();
        all.forEach(result::add);
        return result;
    }


    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        if(message.getMessageId() == null){
            message.setMessageId(UUID.randomUUID());
        }
        return messageRepository.save(message);
    }

    @GetMapping("{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageRepository.findById(id).orElse(null);
    }
}
