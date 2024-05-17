package io.huyvu.hicha;


public class MapperUtils {
    public static <T, S> T from(S source) {
        return new UnknownSourceTargetType().from(source);
    }

    public static <S> KnownSourceType<S> map(Class<S> sourceType) {
        return new KnownSourceType<>(sourceType);
    }

    public static <T> KnownTargetType<T> to(Class<T> targetType) {
        return new KnownTargetType<>(targetType);
    }



    public static class UnknownSourceTargetType {
        @SuppressWarnings("unchecked")
        public <T, S> T from(S source) {
            System.out.println("Map: " + source.getClass() + " to unknown");
            return null;
        }
    }

    public static class KnownTargetType<T> {
        private final Class<T> targetType;

        public KnownTargetType(Class<T> targetType) {
            this.targetType = targetType;
        }

        @SuppressWarnings("unchecked")
        public <S> T from(S source) {
            System.out.println("Map: " + source.getClass() + " to " + targetType);
            return null;
        }
    }

    public static class KnownSourceType<S> {
        private final Class<S> sourceType;

        public KnownSourceType(Class<S> sourceType) {
            this.sourceType = sourceType;
        }

        @SuppressWarnings("unchecked")
        public <T> T from(S source) {
            System.out.println("Map: " + source.getClass() + " to unknown");
            return null;
        }

        public <T> KnownSourceTargetType<S, T> to(Class<T> targetType) {
            return new KnownSourceTargetType<>(sourceType, targetType);
        }
    }

    public static class KnownSourceTargetType<S, T> {
        private final Class<S> sourceType;
        private final Class<T> targetType;

        public KnownSourceTargetType(Class<S> sourceType, Class<T> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @SuppressWarnings("unchecked")
        public T from(S source) {
            System.out.println("Map: " + sourceType + " to " + targetType);
            return null;
        }
    }
}


