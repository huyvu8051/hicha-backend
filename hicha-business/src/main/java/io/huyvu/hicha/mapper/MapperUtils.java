package io.huyvu.hicha.mapper;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class MapperUtils {
    @SneakyThrows
    public static <S, T> T map(S dto, Class<T> clazz) {
        Constructor<T> ctor = clazz.getConstructor(String.class);
        return ctor.newInstance(new Object[]{});
    }
}
