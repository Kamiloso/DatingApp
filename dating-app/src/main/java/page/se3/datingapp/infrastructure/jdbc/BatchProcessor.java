package page.se3.datingapp.infrastructure.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.core.util.SplitUtils;
import page.se3.datingapp.domain.config.AppConfig;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BatchProcessor {

    private final AppConfig appConfig;

    public <T> void process(List<T> sourceList, Consumer<List<T>> processor) {
        int batchSize = appConfig.limits().batchSize();
        SplitUtils.batchProcess(sourceList, batchSize, processor);
    }

    public <T, R> List<R> convert(List<T> sourceList, Function<List<T>, List<R>> converter) {
        int batchSize = appConfig.limits().batchSize();
        return SplitUtils.batchConvert(sourceList, batchSize, converter);
    }
}
