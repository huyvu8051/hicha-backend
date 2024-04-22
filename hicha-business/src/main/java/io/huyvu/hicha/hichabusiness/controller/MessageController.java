package io.huyvu.hicha.hichabusiness.controller;

import io.huyvu.hicha.hichabusiness.model.MessageContent;
import io.huyvu.hicha.hichabusiness.model.MessageInsert;
import io.huyvu.hicha.hichabusiness.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @PostMapping
    String send(@RequestBody MessageContent message) {
        var messageInsert = new MessageInsert(message.conversationId(), 1l, message.messageText());
        messageRepository.insertMessage(messageInsert);
        return "Success";
    }
}
