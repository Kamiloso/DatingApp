package page.se3.datingapp.application.service.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.SubService;
import page.se3.datingapp.application.repository.GlobalRepository;

@Service
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
