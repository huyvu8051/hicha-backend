package io.huyvu.hicha.repository.repo;

import io.huyvu.hicha.repository.model.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findByConversationId(long conversationId);
    void save(Message message);
}
