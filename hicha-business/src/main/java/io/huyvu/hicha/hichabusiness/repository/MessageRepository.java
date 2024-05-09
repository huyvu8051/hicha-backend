package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageRepository {
    @Select("""
            select *
            from messages
            where conversation_id = #{conversationId}
            limit 100""")
    List<Message> findByConversationId(long conversationId);


    @Insert("""
            insert into messages
            set conversation_id = #{conversationId},
                sender_id = #{senderId},
                message_text = #{messageText},
                sent_at = #{sentAt}
            """)
    void save(Message message);
}
