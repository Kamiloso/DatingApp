package page.se3.datingapp.domain.core.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Streams {

    private Streams() {}

    public static <T, R> R map(
            T value,
            Function<T, R> mapValue
    ) {
        return mapValue.apply(value);
    }

    public static <T, R> List<R> toList(
            Collection<T> collection,
            Function<T, R> mapValue
    ) {
        return collection.stream()
                .map(mapValue)
                .toList();
    }

    public static <T, R> Set<R> toSet(
            Collection<T> collection,
            Function<T, R> mapValue
    ) {
        return collection.stream()
                .map(mapValue)
                .collect(Collectors.toSet());
    }

    public static <T, K, R> Map<K, R> toMap(
            Collection<T> collection,
            Function<T, K> mapKey,
            Function<T, R> mapValue
    ) {
        return collection.stream()
                .collect(Collectors.toMap(mapKey, mapValue));
    }
}
