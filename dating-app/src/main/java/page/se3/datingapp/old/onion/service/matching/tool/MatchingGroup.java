package page.se3.datingapp.old.onion.service.matching.tool;

import page.se3.datingapp.old.model.struct.post.MatchGroupPost;

import java.time.Instant;

public record MatchingGroup(
        Integer matchCountLimit,
        Integer readyIndex
) {

    public MatchGroupPost derivePost() {
        Instant matchingTime = Instant.now();
        return new MatchGroupPost(matchingTime, matchCountLimit, readyIndex);
    }
}
