package io.huyvu.hicha.repository.impl.repo;

import io.huyvu.hicha.repository.model.Message;
import io.huyvu.hicha.repository.repo.MessageRepository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageRepositoryImpl extends MessageRepository {
    @Override
    @Select("""
            select *
            from messages
            where conversation_id = #{conversationId}
            order by message_id desc
            limit 100""")
    List<Message> findByConversationId(long conversationId);

    @Override
    @Insert("""
            insert into messages
            set conversation_id = #{conversationId},
                sender_id = #{senderId},
                message_text = #{messageText},
                sent_at = #{sentAt}""")
    void save(Message message);
}
