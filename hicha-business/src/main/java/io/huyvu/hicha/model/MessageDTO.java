package io.huyvu.hicha.model;

import io.huyvu.hicha.mapper.MessageMapper;
import io.huyvu.hicha.repository.model.Message;
import io.huyvu.smart.mapstruct.SmartMapStruct;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@SmartMapStruct
public class MessageDTO implements MsgMapper {
    Integer conversationId;
    Integer senderId;
    String messageText;

    /*public <R> R mapTo(Class<R> type) {
        return Mappable.mapTo(this, type);
    }*/
}

interface MsgMapper {
    default <R> R mapTo(Class<R> type) {
        throw new RuntimeException("Not implemented");
    }
}

class MapperMethodEntry {
    Object mapper;
    Method method;

    public MapperMethodEntry(Object mapper, Method method) {
        this.mapper = mapper;
        this.method = method;
    }
}

@Mapper
class Mappable {
    private static final Map<Class<?>, Map<Class<?>, MapperMethodEntry>> mapperMethods = new HashMap<>();

    static {
        MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        try {
            Method method = mapper.getClass().getDeclaredMethod("map", MessageDTO.class);
            MapperMethodEntry mapperMethodEntry = new MapperMethodEntry(mapper, method);

            var entry = new HashMap<Class<?>, MapperMethodEntry>();
            entry.put(Message.class, mapperMethodEntry);

            mapperMethods.put(MessageDTO.class, entry);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R mapTo(Object source, Class<R> type) {
        Object instance = null;
        try {
            var classMethodMap = mapperMethods.get(source.getClass());
            var entry = classMethodMap.get(type);
            Object result = entry.method.invoke(entry.mapper, source);
            return (R) result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


}