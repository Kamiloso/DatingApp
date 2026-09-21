package page.se3.datingapp.old.core.util;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class DivisionUtils {

    private DivisionUtils() {}

    public static <T> List<List<T>> splitIntoBatches(List<T> sourceList, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException();
        }

        int batchAmount = (sourceList.size() + batchSize - 1) / batchSize;

        return IntStream.range(0, batchAmount)
                .mapToObj(i -> sourceList.subList(i * batchSize, Math.min(sourceList.size(), (i + 1) * batchSize)))
                .toList();
    }

    public static <T> List<T> joinBatches(List<List<T>> batches) {
        return batches.stream()
                .flatMap(List::stream)
                .toList();
    }

    public static <T> void batchProcess(
            List<T> sourceList,
            int batchSize,
            Consumer<List<T>> consumer
    ) {
        splitIntoBatches(sourceList, batchSize).forEach(consumer);
    }

    public static <T, R> List<R> batchConvert(
            List<T> sourceList,
            int batchSize,
            Function<List<T>, List<R>> converter
    ) {
        List<List<R>> resultBatches = splitIntoBatches(sourceList, batchSize).stream()
                .map(converter)
                .toList();

        return joinBatches(resultBatches);
    }
}
