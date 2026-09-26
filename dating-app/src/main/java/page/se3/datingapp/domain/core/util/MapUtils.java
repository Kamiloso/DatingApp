package page.se3.datingapp.domain.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MapUtils {

    private MapUtils() {}

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
