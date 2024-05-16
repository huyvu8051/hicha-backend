package io.huyvu.hicha.model;

import io.huyvu.hicha.mapper.MessageMapper;
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
public class MessageDTO extends Mappable {
    Integer conversationId;
    Integer senderId;
    String messageText;

    public <R> R mapTo(Class<R> type) {
        Object instance = null;
        try {
            instance = Mappable.getMapMethodBySourceTargetType(this, type);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return (R) instance;
    }
}

class MethodSourceTarget<S, R> {
    Class<S> sourceType;
    Class<R> returnType;

    public MethodSourceTarget(Class<S> src, Class<R> target) {
        this.sourceType = src;
        this.returnType = target;
    }

}

@Mapper
class Mappable {
    private static final Map<MethodSourceTarget<Object, Object>, Method> mapperMethods = new HashMap<>();

    static {
        MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        try {
            Method map = mapper.getClass().getDeclaredMethod("map", MessageDTO.class);
            //mapperMethods.put()

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public static <S, R> R getMapMethodBySourceTargetType(S src, Class<R> target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
         MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        Method method = mapper.getClass().getMethod("map", MessageDTO.class);
        Object result = method.invoke(mapper, src);
        return (R) result;
    }
}