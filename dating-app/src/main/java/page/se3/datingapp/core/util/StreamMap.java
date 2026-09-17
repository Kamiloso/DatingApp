package page.se3.datingapp.core.util;

import java.util.List;
import java.util.function.Function;

public final class StreamMap {

    private StreamMap() {}

    public static <T, R> List<R> fromList(List<T> sourceList, Function<T, R> mapper) {
        return sourceList.stream()
                .map(mapper)
                .toList();
    }
}
