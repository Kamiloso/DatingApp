package page.se3.datingapp.infrastructure.database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.config.LimitConfig;
import page.se3.datingapp.domain.core.util.SplitUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BatchProcessor {

    private final LimitConfig limitConfig;

    public <T> void process(List<T> sourceList, Consumer<List<T>> processor) {
        int batchSize = limitConfig.batchSize();
        SplitUtils.batchProcess(sourceList, batchSize, processor);
    }

    public <T, R> List<R> convert(List<T> sourceList, Function<List<T>, List<R>> converter) {
        int batchSize = limitConfig.batchSize();
        return SplitUtils.batchConvert(sourceList, batchSize, converter);
    }
}
