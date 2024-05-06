package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageCassandraRepository extends CrudRepository<Message, Long> {
    List<Message> findByMessageId(long messageId);
}
