package io.huyvu.hicha.hichabusiness.mapper;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import io.huyvu.hicha.hichabusiness.repository.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(source = "username", target = "username")
    UserEntity toEntity(UserDTO dto);
}
