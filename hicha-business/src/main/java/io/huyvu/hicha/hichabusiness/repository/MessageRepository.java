package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.Message;
import io.huyvu.hicha.hichabusiness.model.MessageInsert;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageRepository {

    @Select("""
            select message_id
                  ,conversation_id
                  ,sender_id
                  ,message_text
                  ,sent_at
              from hicha.messages
             where conversation_id = #{conversationId}
             LIMIT #{limit} OFFSET #{offset}""")
    List<Message> selectMessages(long conversationId, int offset, int limit);

    @Insert("""
            insert into hicha.messages (conversation_id, sender_id, message_text)
            values (#{conversationId}, #{senderId}, #{messageText})""")
    long insertMessage(MessageInsert message);

    @Select("""
            select last_insert_id()""")
    long selectLastInsertId();

}
