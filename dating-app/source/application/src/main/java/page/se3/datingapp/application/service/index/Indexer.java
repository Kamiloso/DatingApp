package page.se3.datingapp.application.service.index;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.repository.GlobalRepository;

@SubService
@RequiredArgsConstructor
public class Indexer {

    private final GlobalRepository globalRepository;

    public long getByKey(String key) {
        return globalRepository.findByKey(key)
                .map(Indexer::forgivingParseLong)
                .orElse(0L);
    }
    
    public long getIncrementedByKeyForUpdate(String key) {
        long counter = globalRepository.findByKeyForUpdate(key)
                .map(Indexer::forgivingParseLong)
                .orElse(0L);

        String value = String.valueOf(++counter);
        globalRepository.saveOrReplace(key, value);

        return counter;
    }

    private static long forgivingParseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
