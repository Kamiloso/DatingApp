package page.se3.datingapp.application.service.matching.indexer;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.interfaces.KeyValueRepository;

@SubService
@RequiredArgsConstructor
public class MatchingIndexer {

    private static final String MATCHING_INDEX = "matching-index";

    private final KeyValueRepository keyValueRepository;

    public int getFuture() {
        int current = keyValueRepository.findByKey(MATCHING_INDEX)
                .map(MatchingIndexer::forgivingParseInt)
                .orElse(0);

        return current + 1;
    }

    public int getIncrementedForUpdate() {
        int counter = keyValueRepository.findByKeyForUpdate(MATCHING_INDEX)
                .map(MatchingIndexer::forgivingParseInt)
                .orElse(0);

        counter++;
        keyValueRepository.saveOrReplace(MATCHING_INDEX, String.valueOf(counter));

        return counter;
    }

    private static int forgivingParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
