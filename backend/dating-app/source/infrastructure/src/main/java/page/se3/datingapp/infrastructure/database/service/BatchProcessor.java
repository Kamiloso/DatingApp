package page.se3.datingapp.infrastructure.database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.config.app.limiting.capacity.CapacityConfig;
import page.se3.datingapp.domain.core.util.Divisions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BatchProcessor {

    private final CapacityConfig capacityConfig;

    public <T> void process(List<T> sourceList, Consumer<List<T>> processor) {
        int batchSize = capacityConfig.maxBatchSize();
        Divisions.batchProcess(sourceList, batchSize, processor);
    }

    public <T, R> List<R> convert(List<T> sourceList, Function<List<T>, List<R>> converter) {
        int batchSize = capacityConfig.maxBatchSize();
        return Divisions.batchConvert(sourceList, batchSize, converter);
    }
}
