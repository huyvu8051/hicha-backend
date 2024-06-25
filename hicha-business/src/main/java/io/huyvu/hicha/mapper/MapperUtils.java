package io.huyvu.hicha.mapper;

import lombok.SneakyThrows;

public class MapperUtils {

    @SneakyThrows
    public static MapperBuilder from(Object... sources) {
        return new MapperBuilder();
    }

    public static class MapperBuilder {
        public MapperBuilder map(String s, String t) {
            return this;
        }

        public <T> T build() {
            return null;
        }

    }
}
