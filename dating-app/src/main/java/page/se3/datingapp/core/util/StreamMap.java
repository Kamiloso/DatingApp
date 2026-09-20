package page.se3.datingapp.core.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class StreamMap {

    private StreamMap() {}

    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream()
                .map(mapper)
                .toList();
    }
}
