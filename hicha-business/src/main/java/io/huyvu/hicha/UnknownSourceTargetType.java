package io.huyvu.hicha;

public class UnknownSourceTargetType {

    @SuppressWarnings("unchecked")
    public <T, S> T from(S source) {
        return (T) source;
    }

    public <T> KnownTargetType<T> to(Class<T> targetType) {
        return new KnownTargetType<>(targetType);
    }

    public <S> KnownSourceType<S> map(Class<S> sourceType) {
        return new KnownSourceType<>(sourceType);
    }


    public static class KnownTargetType<T> {

        public KnownTargetType(Class<T> targetType) {

        }

        @SuppressWarnings("unchecked")
        public T from(Object source) {
            return (T) source;
        }
    }

    public static class KnownSourceType<S> {
        public KnownSourceType(Class<S> sourceType){
        }

        @SuppressWarnings("unchecked")
        public <T> T from(S source){
            return (T) source;
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
