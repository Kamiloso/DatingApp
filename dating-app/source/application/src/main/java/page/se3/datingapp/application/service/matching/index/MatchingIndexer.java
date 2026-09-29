package page.se3.datingapp.application.service.matching.index;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.service.index.Indexer;

@SubService
@RequiredArgsConstructor
public class MatchingIndexer {

    private static final String MATCHING_INDEX_KEY = "matching-index";

    private final Indexer indexer;

    public int getFuture() {
        return (int) indexer.getByKey(MATCHING_INDEX_KEY) + 1;
    }

    public int getIncrementedForUpdate() {
        return (int) indexer.getIncrementedByKeyForUpdate(MATCHING_INDEX_KEY);
    }
}
