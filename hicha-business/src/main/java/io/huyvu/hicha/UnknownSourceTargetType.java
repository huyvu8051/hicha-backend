package io.huyvu.hicha;

import io.huyvu.hicha.model.MessageDTO;
import io.huyvu.hicha.repository.model.Message;

public class UnknownSourceTargetType {

    @SuppressWarnings("unchecked")
    public <T, S> T from(S source) {
        return (T) source;
    }

    public static class KnownTargetType<T> {
        private final Class<T> targetType;
        public KnownTargetType(Class<T> targetType) {
            this.targetType = targetType;
        }

        @SuppressWarnings("unchecked")
        public T from(Object source) {
            return (T) source;
        }

        public <S> KnownSourceTargetType<S, T> map(Class<S> sourceType) {
            return new KnownSourceTargetType<>(sourceType, targetType);
        }
    }

    public static class KnownSourceType<S> {
        private final Class<S> sourceType;
        public KnownSourceType(Class<S> sourceType){
            this.sourceType = sourceType;
        }

        @SuppressWarnings("unchecked")
        public <T> T from(S source){
            return (T) source;
        }

        public <T> KnownSourceTargetType<S, T> to(Class<T> targetType) {
            return new KnownSourceTargetType<>(sourceType, targetType);
        }
    }

    public static class KnownSourceTargetType<S, T> {
        public KnownSourceTargetType(Class<S> sourceType, Class<T> targetType){
        }

        @SuppressWarnings("unchecked")
        public T from(S source){
            return (T) source;
        }
    }
}
