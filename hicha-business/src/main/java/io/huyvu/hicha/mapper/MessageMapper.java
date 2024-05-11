package io.huyvu.hicha.mapper;

import io.huyvu.hicha.model.MessageDTO;
import io.huyvu.hicha.repository.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    Message map(MessageDTO dto);
}